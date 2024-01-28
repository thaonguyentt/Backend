package base.api.common;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.sanctionco.jmail.JMail;

public class CommonValidator {

  // Use JMail library for email validating
  public static boolean isEmail(String email) {
    return JMail.isValid(email);
  }

  // Use libphonenumber for phone number validating
  public static boolean isVnMobilePhoneNumber(String number) {
    var util = PhoneNumberUtil.getInstance();
    PhoneNumber phoneNumber = null;
    try {
      phoneNumber = util.parse(number, "VN");
      return util.isValidNumberForRegion(phoneNumber, "VN");
    } catch (NumberParseException e) {
      return false;
    }
  }
}
