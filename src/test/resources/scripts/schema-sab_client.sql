
--
-- Table structure for table `SAB_CLIENT`
--

CREATE TABLE IF NOT EXISTS sab_client3
(
  gid                    VARCHAR(255) NOT NULL PRIMARY KEY,
  activation_code        VARCHAR(20)  NULL,
  authorized             BIT          NOT NULL,
  authorized_by          VARCHAR(30)  NULL,
  authorized_date        DATETIME     NULL,
  birthday               DATE         NULL,
  block_reason           VARCHAR(255) NULL,
  blocked                BIT          NOT NULL,
  blocked_till           DATETIME     NULL,
  ch_pwd_code_end_date   DATETIME     NULL,
  change_password_code   VARCHAR(255) NULL,
  contragent_id          VARCHAR(255) NULL,
  email                  VARCHAR(255) NULL,
  email_approval_code    VARCHAR(255) NULL,
  email_approval_date    DATETIME     NULL,
  id                     VARCHAR(255) NULL,
  identification_date    DATETIME     NULL,
  invalid_tries_count    INT          NOT NULL,
  last_login_date        DATETIME     NULL,
  login                  VARCHAR(255) NULL,
  login_count            INT          NOT NULL,
  mobile                 VARCHAR(20)  NULL,
  mobile_approval_code   VARCHAR(255) NULL,
  mobile_approval_date   DATETIME     NULL,
  name                   VARCHAR(64)  NULL,
  name_lat               VARCHAR(64)  NULL,
  original_mobile_number VARCHAR(20)  NULL,
  parent_name            VARCHAR(64)  NULL,
  parent_name_lat        VARCHAR(64)  NULL,
  passport               VARCHAR(20)  NULL,
  password               VARCHAR(255) NULL,
  password_changed       DATETIME     NULL,
  payment_templates      LONGTEXT     NULL,
  properties             LONGTEXT     NULL,
  registration_date      DATETIME     NULL,
  resident               BIT          NOT NULL,
  status                 VARCHAR(255) NULL,
  surname                VARCHAR(64)  NULL,
  surname_lat            VARCHAR(64)  NULL,
  tax_id                 VARCHAR(255) NULL,
  vip                    BIT          NOT NULL,
  CONSTRAINT UK_lnh1abh8mqbj320vjeqd0cng5 UNIQUE (change_password_code),
  CONSTRAINT UK_6ljhd5lmhd3571o2vk5nac1uk UNIQUE (email_approval_code),
  CONSTRAINT UK_82i9qf6px6md8wue5f3gn4kn UNIQUE (id),
  CONSTRAINT UK_gjxpwgrfao0chg78pgy3kyg6q UNIQUE (login),
  CONSTRAINT UK_s3de37s2ovyeuvavducwtw5mg UNIQUE (mobile_approval_code)
) DEFAULT CHARSET=utf8;

CREATE INDEX CLIENT_ACTIVATION_CODE_IDX ON sab_client3 (activation_code);
CREATE INDEX CLIENT_AUTHORIZED_IDX ON sab_client3 (authorized);
CREATE INDEX CLIENT_BIRTHDAY_INDEX ON sab_client3 (birthday);
CREATE INDEX CLIENT_CH_PWD_CODE_END_DATE_INDEX ON sab_client3 (ch_pwd_code_end_date);
CREATE INDEX CLIENT_EMAIL_APPROVAL_IDX ON sab_client3 (email_approval_code);
CREATE INDEX CLIENT_EMAIL_DATE_INDEX ON sab_client3 (email_approval_date);
CREATE INDEX CLIENT_IDENT_DATE_INDEX ON sab_client3 (identification_date);
CREATE INDEX CLIENT_LAST_LOGIN_DATE_INDEX ON sab_client3 (last_login_date);
CREATE INDEX CLIENT_MOBILE_APPROVAL_IDX ON sab_client3 (mobile_approval_code);
CREATE INDEX CLIENT_MOBILE_DATE_INDEX ON sab_client3 (mobile_approval_date);
CREATE INDEX CLIENT_MOBILE_INDEX ON sab_client3 (mobile);
CREATE INDEX CLIENT_PASSPORT_INDEX ON sab_client3 (passport);
CREATE INDEX CLIENT_REG_DATE_INDEX ON sab_client3 (registration_date);
CREATE INDEX CLIENT_RESIDENT_INDEX ON sab_client3 (resident);
CREATE INDEX CLIENT_STATUS_INDEX ON sab_client3 (status);
CREATE INDEX CLIENT_VIP_INDEX ON sab_client3 (vip);
CREATE INDEX TAX_CODE_INDEX ON sab_client3 (tax_id);
