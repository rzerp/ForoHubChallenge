package com.mixidev.forohubchallenge.infra.error;

public class IntegrityValidation extends RuntimeException {
    public IntegrityValidation(String s) {
        super(s);
    }
}