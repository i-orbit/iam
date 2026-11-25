package com.inmaytide.orbit.iam.module.tenant.license;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 校验离线 License 文件签名并解析授权内容。
 *
 * @author inmaytide
 * @since 2026/11/25
 */
@Component
@RequiredArgsConstructor
public class TenantLicenseVerifier {

    private final TenantLicenseProperties properties;

    private final ObjectMapper objectMapper;

    public TenantLicensePayload verify() {
        if (StringUtils.isAnyBlank(properties.getFile(), properties.getPublicKey())) {
            throw new IllegalStateException("Tenant license file or public key is not configured");
        }
        try {
            TenantLicenseFile licenseFile = objectMapper.readValue(Path.of(properties.getFile()), TenantLicenseFile.class);
            if (licenseFile == null || StringUtils.isAnyBlank(licenseFile.getPayload(), licenseFile.getSignature())) {
                throw new IllegalStateException("Tenant license file is incomplete");
            }
            byte[] payload = Base64.getDecoder().decode(licenseFile.getPayload());
            byte[] signature = Base64.getDecoder().decode(licenseFile.getSignature());
            PublicKey publicKey = KeyFactory.getInstance("RSA")
                    .generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(properties.getPublicKey())));
            Signature verifier = Signature.getInstance("SHA256withRSA");
            verifier.initVerify(publicKey);
            verifier.update(payload);
            if (!verifier.verify(signature)) {
                throw new IllegalStateException("Tenant license signature verification failed");
            }
            TenantLicensePayload license = objectMapper.readValue(payload, TenantLicensePayload.class);
            if (license == null || StringUtils.isAnyBlank(license.getTenantCode(), license.getEdition())) {
                throw new IllegalStateException("Tenant license payload is incomplete");
            }
            if (StringUtils.isNotBlank(license.getMachineCode())
                    && !license.getMachineCode().equals(properties.getMachineCode())) {
                throw new IllegalStateException("Tenant license machine code mismatch");
            }
            return license;
        } catch (GeneralSecurityException | IllegalArgumentException | JacksonException e) {
            throw new IllegalStateException("Failed to verify tenant license", e);
        }
    }

}
