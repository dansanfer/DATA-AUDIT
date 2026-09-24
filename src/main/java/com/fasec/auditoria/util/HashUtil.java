package com.fasec.auditoria.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {

    private HashUtil() {
        // Construtor privado para classe utilitária
    }

    public static String gerarHashSha256(String... valores) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            StringBuilder dadosConcatenados = new StringBuilder();

            for (String valor : valores) {
                if (valor != null) {
                    dadosConcatenados.append(valor).append("|");
                }
            }

            byte[] encodedhash = digest.digest(dadosConcatenados.toString().getBytes(StandardCharsets.UTF_8));

            // Converte bytes para formato hexadecimal legível
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Algoritmo de hash SHA-256 não disponível no ambiente", e);
        }
    }
}