/*
 * Copyright (c) 2013-2015 Scratch Wireless, 2600hz.
 */
package org.scratchwireless.kazoo.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.scratchwireless.kazoo.client.domain.Account;
import org.scratchwireless.kazoo.client.domain.Account.BillingModes;
import org.scratchwireless.kazoo.client.impl.KazooClientImpl;

/**
 * TODO Add class summary here
 *
 * @author bpdavis
 */
@Slf4j
public class KazooClientTest {
   private static final boolean deleteTestAccountsDuringCleanup = false;
   /* Login credentials for local VM */
   private static final String KAZOO_ADMIN_PASSWORD = "admin1234";
   private static final String KAZOO_ADMIN_REALM = "sip.scratch.com";
   private static final String KAZOO_ADMIN_USER = "admin";

   private static final String KAZOO_URI = "http://10.10.10.100:8000/v2";

   private static final String TEST_REALM = "my.test.org";

   private static final String getRandomTestAccountName() {
      return "test" + UUID.randomUUID().toString().replaceAll("-", "");
   }

   private KazooClient client;

   private List<String> createdAccounts = new ArrayList<String>();

   private Account createAccount() throws IOException {
      final String accountName = KazooClientTest.getRandomTestAccountName();

      Account account = new Account();
      account.setId(UUID.randomUUID().toString().replaceAll("-", ""));
      account.setName(accountName);
      account.setRealm(accountName + "." + KazooClientTest.TEST_REALM);

      account = client.createAccount(account);
      Assert.assertNotNull(account);
      createdAccounts.add(account.getId());
      System.out.println("Created account: " + account);
      return account;
   }

   private void delete(final Account account) throws IOException {
      if (account == null) {
         return;
      }
      List<Account> children = client.getAccountChildren(account.getId());
      if (children != null) {
         for (Account child : children) {
            delete(child);
         }
      }
      client.deleteAccount(account.getId());
   }

   @Test
   public void deleteAllAccountChildren() throws IOException {
      Account loggedInAccount = client.getConnection().getLoggedInAccount();
      Assert.assertNotNull(loggedInAccount);
      final String accountId = loggedInAccount.getId();
      List<Account> children = client.getAccountChildren(accountId);
      Assert.assertNotNull(children);
      System.out.println("Children: " + children);
      for (Account account : children) {
         delete(account);
      }
   }

   @Before
   public void setUp() throws IOException {
      client = new KazooClientImpl(KazooClientTest.KAZOO_URI, KazooClientTest.KAZOO_ADMIN_USER, KazooClientTest.KAZOO_ADMIN_PASSWORD, KazooClientTest.KAZOO_ADMIN_REALM);
      Assert.assertNotNull(client.getConnection().getAuthToken());
   }

   @After
   public void tearDown() throws IOException {
      if (KazooClientTest.deleteTestAccountsDuringCleanup && createdAccounts != null) {
         for (String id : createdAccounts) {
            client.deleteAccount(id);
         }
      }
   }

   @Test
   public void testAccountCreation() throws IOException {
      createAccount();
   }

   @Test
   public void testAccountMove() throws IOException {
      Account account1 = createAccount();
      Account account2 = createAccount();
      client.moveAccount(account2.getId(), account1.getId());
      KazooClientTest.log.debug("{} should now be the parent of {}", account1.getName(), account2.getName());
   }

   @Test
   public void testAccountUpdate() throws IOException {
      Account account = createAccount();
      account.setBillingMode(BillingModes.MANUAL);
      account = client.updateAccount(account);
      Assert.assertNotNull(account);

      System.out.println("Updated account: " + account);
   }

   @Test
   public void testListAccounts() throws IOException {
      Account loggedInAccount = client.getConnection().getLoggedInAccount();
      Assert.assertNotNull(loggedInAccount);
      System.out.println("Logged in account: " + loggedInAccount);
      final String accountId = loggedInAccount.getId();
      List<Account> children = client.getAccountChildren(accountId);
      Assert.assertNotNull(children);
      System.out.println("Children: " + children);
   }
}
