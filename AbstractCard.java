
package com.rae.android.locker;

/*
 * Copyright (c) 2011, Gerald R. Lyons. All rights reserved.
 *
 */

import android.content.Context;
import java.util.Calendar;

/**
 *  Implements the mutable abstract card class.
 *
 *  It is intended that this class be a safe candidate for extension via inheritance:
 *
 *      The implementation does not contain self-use of any overridable methods.
 *      The relevant internal state is exposed as protected fields.
 *      The implementation supplies a no argument constructor to allow implementation of interfaces such as Parcelable
 *
 *  This class is not thread safe.  Access to it's state is not synchronized.
 *
 *  The encode method, which returns an encoded card object, must be implemented when subclassed.
 *  Similarly, the field state methods nameIsSet, codeIsSet, typeIsSet, and dateIsSet must also be
 *  defined in a concrete implementation.
 */

public abstract class AbstractCard {

	protected String name;
	protected String code;
	protected String type;
	protected Calendar date;

    //
    // Constructors
    //

    /**
     * Construct a new mutable instance of this class with fields initialize to default values.
     */
     public AbstractCard() { }

    /**
     * Construct a new mutable instance of this class with name, code, and type fields initialized
     * to the supplied parameter values.
     *
     * @param name the card name, or null
     * @param code the card bar code, or null
     * @param type the bar code type, or null
     */
    public AbstractCard(String name, String code, String type) {
        this.name = name;
        this.code = code;
        this.type = type;
    }

    //
    // Accessors
    //

    /**
     * Return the name field for this instance of the class
     *
     * @return the string representation of the this name field, or null
     */
    public String getName() {
        return name;
    }

    /**
     * Return the bar code vale for this instance of the class
     *
     * @return the string representation of the this code field, or null
     */
    public String getCode() {
        return code;
    }

    /**
     * Return the bar code type value of this instance of the class
     *
     * @return the string representation of the this name field, or null
     */
    public String getType() {
        return type;
    }

    /**
     * Return a defensive copy of the date field for or this instance of the class
     *
     * @return a unique Calendar representation of the instance date, or null
     */
    public Calendar getDate() {
        Calendar value = null;
        if (date != null) {
            value = Calendar.getInstance();
            value.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_WEEK));
        }
        return value;
    }

    /**
     * Return an encoded representation of this instance of the class.
     *
     * The object will have the specified width and height.
     *
     * @param context
     * @param targetWidth
     * @param targetHeight
     * @return an encoded representation of the instance
     */
    public abstract Object encode(Context context, int targetWidth, int targetHeight);


    //
    // States
    //

    /**
     * Test if the value of the indicated field in this instance has been "set"
     *
     * @return true if the field value is set, or false
     */
    abstract boolean nameIsSet();
    abstract boolean typeIsSet();
    abstract boolean codeIsSet();
    
     /**
     * Compare this instance of the date to the current date.
     *
     * Returns true if this date has expired (is earlier than the current date), or false
     * if it has not expired (is not earlier than the current date).
     *
     * @return true if the this date is earlier than the current date, or false
     */
    public boolean dateIsExpired()
	{
		boolean result = false;
		if (date != null) {
			result = date.before(Calendar.getInstance());
		}
		return result;
	}

    //
    // Mutators
    //

    /**
     * Set the card name field for this instance of the class.
     *
     * The parent class is responsible for validation of the supplied code parameter.
     *
     * @param code a string representation of the card name, or null to clear
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the card code field for this instance of the class.
     *
     * The parent class is responsible for validation of the supplied code parameter.
     *
     * @param code a string representation of the card bar code, or null to clear
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Set the card code type field for this instance of the class.
     *
     * The client or parent class is responsible for validation of the supplied type parameter.
     *
     * @param type a string representation of the card code type, or null to clear
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Initializes the expiration date field for this instance of the class
     *
     * The date field is initialized with the supplied year, month, and date parameters. The client
     * or parent class is responsible for validation of the supplied date parameters.
     *
     * @param year a Calendar.YEAR representation of the expiration date
     * @param month a Calendar.MONTH representation of the month field of the expiration date
     * @param day a Calendar.DAY_OF_WEEK representation of the day field of the expiration date
     */
    final public void setDate(int year, int month, int day) {
        if (date == null) 	{
            date = Calendar.getInstance();
        }
        date.set(year, month, day);
    }

    /**
     *  Defensively Initializes the expiration date field for this instance of the class
     *
     *  When not null, the YEAR, MONTH and DAY_OF_WEEK fields of the supplied Calendar object are
     *  used to initialize this expiration date field.  The client or super is responsible for
     *  validation of the supplied date parameter.
     *
     * @param date a Calendar representation of the expiration date, or null to clear
     */
    public void setDate(Calendar date) {
        if (date != null) {
            if (this.date == null) 	{
                this.date = Calendar.getInstance();
            }
            this.date.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_WEEK));
        }
        else {
            this.date = null;
        }
    }

    //
    // Overrides
    //

    /**
     * Returns the string representation of this instance of the class.
     *
     * If any of the fields of this instance are not "set", they are omitted from the representation.
     * This representation is subject to change, but the following may be regarded as typical:
     *
     *     "Name:CVS Code:384038939120 Type:EAN_13"
     *
     * @return string representation of this instance
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Name: ");
        if (name != null) {s.append(name);}
        if (code != null) {s.append("Code: " + code);}
        if (type != null) {s.append("Type: " + type);}
        return s.toString();
    }

    /**
     * Performs a logical comparison of this instance of the class and the supplied object.
     *
     * A field by field comparison is performed to determine the equality of the objects.
     *
     * @param o  the object to compare to this instance
     * @return  true if the fields of the objects are found to be equal, false if they are not
     */
    @Override
    public boolean equals(Object o)
    {
        if (o instanceof AbstractCard) {
            AbstractCard c = (AbstractCard)o;
            return name == null ? c.name == null : name.equals(c.name)
                    && code == null ? c.code == null : code.equals(c.code)
                    && type == null ? c.type == null : type.equals(c.type)
                    && date == null ? c.date == null : date.equals(c.date);
        }
        else {
            return false;
        }
    }

    /**
     * Digests the data stored in this instance of the class into a single hash value.
     *
     * @return integer hash value
     */
    @Override
    public int hashCode() {
        int rv = 17;
        if (name != null) {rv = 31 * rv + name.hashCode();}
        if (code != null) {rv = 31 * rv + code.hashCode();}
        if (type != null) {rv = 31 * rv + type.hashCode();}
        return rv;
    }
}

