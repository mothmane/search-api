package ma.toto.search.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/** The type Date validator using date format. */
public class DateValidatorUsingDateFormat implements DateValidator {
  private String dateFormat;

  /**
   * Instantiates a new Date validator using date format.
   *
   * @param dateFormat the date format
   */
  public DateValidatorUsingDateFormat(String dateFormat) {
    this.dateFormat = dateFormat;
  }

  @Override
  public boolean isValid(String dateStr) {
    DateFormat sdf = new SimpleDateFormat(this.dateFormat);
    sdf.setLenient(false);
    try {
      sdf.parse(dateStr);
    } catch (ParseException e) {
      return false;
    }
    return true;
  }
}
