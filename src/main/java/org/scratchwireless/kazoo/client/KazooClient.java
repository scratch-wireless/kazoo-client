/*
 * Copyright (c) 2013-2015 Scratch Wireless, 2600hz.
 */
package org.scratchwireless.kazoo.client;

import java.io.IOException;
import java.util.List;

import org.scratchwireless.kazoo.client.domain.Account;
import org.scratchwireless.kazoo.client.impl.KazooConnection;

/**
 * TODO Add class summary here
 *
 * @author bpdavis
 */
public interface KazooClient {

   public Account createAccount(final Account account) throws IOException;

   public Account deleteAccount(final String accountId) throws IOException;

   public Account updateAccount(final Account account) throws IOException;

   public Account getAccount(final String accountId) throws IOException;

   public List<Account> getAccountChildren(final String accountId) throws IOException;

   public List<Account> getAccountTree(final String accountId) throws IOException;

   public void moveAccount(final String accountId, final String newParentAccountId) throws IOException;

   public KazooConnection getConnection();

}
