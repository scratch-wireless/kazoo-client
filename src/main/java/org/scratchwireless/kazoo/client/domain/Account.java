/*
 * Copyright (c) 2013-2015 Scratch Wireless, 2600hz.
 */
package org.scratchwireless.kazoo.client.domain;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import org.scratchwireless.kazoo.client.domain.orig.CallRestriction;

import com.google.api.client.util.Key;
import com.google.api.client.util.Value;

/**
 * TODO Add class summary here
 *
 * @author bpdavis
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Account extends DomainObject {
   /** Manual */
   public enum BillingModes {
      /** limits_only */
      @Value("limits_only")
      LIMITS_ONLY,
      /** manual */
      @Value("manual")
      MANUAL,
      /** normal */
      @Value("normal")
      NORMAL
   }

   @Key("account_id")
   private String accountId;

   @Key("account_name")
   private String accountName;
   /** whether or not numbers may be added to number manager. */
   @Key("wnm_allow_additions")
   private Boolean allowNumberManagerAdditions;
   @Key("apps")
   private List<App> apps;
   /** Billing mode for this account. */
   @Key("billing_mode")
   @Setter(value = AccessLevel.PACKAGE)
   private String billingMode;
   /** The account default caller ID parameters */
   @Key("caller_id")
   private CallerIdSettings callerIdSettings;
   /** Account level call restrictions for each available number classification. */
   @Key("call_restriction")
   private Map<String, CallRestriction> callRestriction;
   /** Time stamp for when this account was created. */
   @Key("created")
   private Long created;
   /** Number of children of this account. */
   @Key("descendants_count")
   @Min(0)
   private Integer descendantsCount;
   /** A list of default rules used to modify dialed numbers. */
   @Key("dial_plan")
   private DialPlan dialPlan;
   /** Determines if the account is currently enabled. */
   @Key("enabled")
   private Boolean enabled;
   /** account id. */
   @Key("id")
   @NotNull
   private String id;
   /** Whether or not this account is a reseller. */
   @Key("is_reseller")
   private Boolean isReseller;
   /** The language for this account. */
   @Key("language")
   private String language;
   /** The default music on hold parameters. */
   @Key("music_on_hold")
   private MusicOnHold musicOnHold;
   /** A friendly name for the account. */
   @NotNull
   @Size(min = 1, max = 128)
   @Key("name")
   private String name;
   @Key("owner_id")
   private String ownerId;
   /** Each property provides functionality that can be applied to calls using the callflow application. */
   @Key("preflow")
   private Map<String, String> preFlow;
   /** The realm of the account, ie: 'account1.2600hz.com'. */
   @Key("realm")
   @Size(min = 4, max = 253)
   @Pattern(regexp = "^([.\\w_-]+)$")
   private String realm;
   /** Associated reseller ID. */
   @Key("reseller_id")
   private String resellerId;
   /** Ringtone Parameters */
   @Key("ringtones")
   private RingTones ringTones;
   /** whether or not this account is a super-admin account. */
   @Key("superduper_admin")
   private Boolean superDuperAdmin;
   /** The default timezone */
   @Key("timezone")
   @Size(min = 5, max = 32)
   private String timeZone;
   // TODO
   @Key("us_metadata")
   private Object uiMetaData;
   // TODO
   @Key("ui_restrictions")
   private Object uiRestrictions;

   public String getAccountId() {
      if (accountId != null) {
         return accountId;
      }
      return id;
   }

   public String getId() {
      if (id != null) {
         return id;
      }
      return accountId;
   }

   public void setBillingMode(final BillingModes mode) {
      billingMode = mode.toString();
   }

   @Override
   public String toString() {
      return super.toString();
   }
}
