package com.grahamcrockford.oco.cli;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Guice;
import com.grahamcrockford.oco.auth.AuthModule;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

public class GenerateSecretKey {

  public static void main(String[] args) throws JsonProcessingException {

    final GoogleAuthenticatorKey key = Guice.createInjector(new AuthModule())
      .getInstance(GoogleAuthenticator.class)
      .createCredentials();

    System.out.println(key.getKey());

  }

}
