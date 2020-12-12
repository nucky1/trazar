package ar.edu.unsl.backend.util;

import java.util.regex.*;

/**
 * This class (Singleton) is used to check the most
 * common user inputs.
 */
public class ExpressionChecker 
{
    private static ExpressionChecker expressionChecker;

    private Pattern pattern;

    private ExpressionChecker() 
    {
        //Empty constructor.
    }

    public static ExpressionChecker getExpressionChecker()
    {
        if(ExpressionChecker.expressionChecker == null)
        {
            ExpressionChecker.expressionChecker = new ExpressionChecker();
        }
        return ExpressionChecker.expressionChecker;
    }

    public boolean onlyNumbers(String string, boolean allowEmpty) 
    {
        boolean ret;

        if(allowEmpty)
            pattern = Pattern.compile("[\\d]*");
        else
            pattern = Pattern.compile("[\\d]+");

        if (pattern.matcher(string).matches())
            ret = true;
        else
            ret = false;

        return ret;
    }

    public boolean onlyNumbers(String string, int digitLimit, boolean allowEmpty)
    {
        boolean ret;

        if(allowEmpty)
            pattern = Pattern.compile("[\\d]{0,"+digitLimit+"}");
        else
            pattern = Pattern.compile("[\\d]{1,"+digitLimit+"}");

        if (pattern.matcher(string).matches())
        {
            System.out.println("true");
            ret = true;
        }
        else
        {
            System.out.println("false");
            ret = false;
        }

        return ret;
    }

    public boolean moneyValue(String string, int leftDigits, int rightDigits, boolean allowEmpty)
    {
        boolean ret;

        if(allowEmpty)
            pattern = Pattern.compile("\\d{0,"+leftDigits+"}(\\.\\d{1,"+rightDigits+"})?");
        else
            pattern = Pattern.compile("\\d{1,"+leftDigits+"}(\\.\\d{1,"+rightDigits+"})?");

        if (pattern.matcher(string).matches())
            ret = true;
        else
            ret = false;

        return ret;
    }


    public boolean moneyValueWithNegative(String string, int leftDigits, int rightDigits, boolean allowEmpty)
    {
        boolean ret;

        if(allowEmpty)
            pattern = Pattern.compile("(-)?\\d{0,"+leftDigits+"}(\\.\\d{1,"+rightDigits+"})?");
        else
            pattern = Pattern.compile("(-)?\\d{1,"+leftDigits+"}(\\.\\d{1,"+rightDigits+"})?");

        if (pattern.matcher(string).matches())
            ret = true;
        else
            ret = false;

        return ret;
    }

    public boolean onlyChars(String string, boolean allowEmpty)
    {
        boolean ret;

        if(allowEmpty)
            pattern = Pattern.compile("[a-zA-Z]*");
        else
            pattern = Pattern.compile("[a-zA-Z]+");

        if (pattern.matcher(string).matches())
            ret = true;
        else
            ret = false;

        return ret;
    }

    public boolean onlyCharsWithWhiteSpace(String string, boolean allowEmpty)
    {
        boolean ret;

        if(allowEmpty)
            pattern = Pattern.compile("[a-zA-Z]*[\\s[a-zA-Z]]*");
        else
            pattern = Pattern.compile("[a-zA-Z]+[\\s[a-zA-Z]]*");

        if (pattern.matcher(string).matches())
            ret = true;
        else
            ret = false;

        return ret;
    }

    public boolean isEmail(String string, boolean allowEmpty) 
    {
        boolean ret;

        if(allowEmpty)
            pattern = Pattern.compile("(^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$|^$)?");
        else
            pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$|^$");

        if (pattern.matcher(string).matches())
            ret = true;
        else
            ret = false;

        return ret;
    }

    public boolean composedName(String string)
    {
        boolean ret;

        pattern = Pattern.compile("^[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+(\\s*[a-zA-ZÀ-ÿ\\u00f1\\u00d1]*)*[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+$");

        if (pattern.matcher(string).matches())
            ret = true; 
        else
            ret = false;
        
        return ret;
    }

    /**
     *
     * Username consists of alphanumeric characters (a-zA-Z0-9), lowercase, or uppercase.
     * Username allowed of the dot (.), underscore (_), and hyphen (-).
     * The dot (.), underscore (_), or hyphen (-) must not be the first or last character.
     * The dot (.), underscore (_), or hyphen (-) does not appear consecutively, e.g., java..regex
     * The number of characters must be between 5 to 20.
     * @param string The string to check.
     * @return true if condition are satisfied, false if not.
     */
    public boolean userName(String string)
    {
        boolean ret;

        pattern = Pattern.compile("^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$");

        if (pattern.matcher(string).matches())
            ret = true; 
        else
            ret = false;
        
        return ret;
    }

    public boolean isCatalogueCode(String string, boolean allowEmpty)
    {
        boolean ret;

        if(allowEmpty)
            pattern = Pattern.compile("([1-6]\\d\\d\\d\\d)?");
        else
            pattern = Pattern.compile("[1-6]\\d\\d\\d\\d");

        if (pattern.matcher(string).matches())
            ret = true;
        else
            ret = false;

        return ret;
    }
}