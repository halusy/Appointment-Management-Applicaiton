package com.example.AMP.helper;

/**
 *
 * This class holds methods to convert the String of a Division to the Division_ID. ChatGPT was very helpful with this type of method, since it consisted mostly of mindless typing.
 *
 * @author Nicholas Ryan, ChatGPT
 * @version 1.0
 */
public class DivisionIdHelper {

    /**
     * This method takes a division string and returns the Division_ID
     *
     * @param division
     * @return
     */
    public static int divisionIdRetriever(String division) {

        if (division.equals("Alabama")) {
            return 1;
        } else if (division.equals("Arizona")) {
            return 2;
        } else if (division.equals("Arkansas")) {
            return 3;
        } else if (division.equals("California")) {
            return 4;
        } else if (division.equals("Colorado")) {
            return 5;
        } else if (division.equals("Connecticut")) {
            return 6;
        } else if (division.equals("Delaware")) {
            return 7;
        } else if (division.equals("District of Columbia")) {
            return 8;
        } else if (division.equals("Florida")) {
            return 9;
        } else if (division.equals("Georgia")) {
            return 10;
        } else if (division.equals("Idaho")) {
            return 11;
        } else if (division.equals("Illinois")) {
            return 12;
        } else if (division.equals("Indiana")) {
            return 13;
        } else if (division.equals("Iowa")) {
            return 14;
        } else if (division.equals("Kansas")) {
            return 15;
        } else if (division.equals("Kentucky")) {
            return 16;
        } else if (division.equals("Louisiana")) {
            return 17;
        } else if (division.equals("Maine")) {
            return 18;
        } else if (division.equals("Maryland")) {
            return 19;
        } else if (division.equals("Massachusetts")) {
            return 20;
        } else if (division.equals("Michigan")) {
            return 21;
        } else if (division.equals("Minnesota")) {
            return 22;
        } else if (division.equals("Mississippi")) {
            return 23;
        } else if (division.equals("Missouri")) {
            return 24;
        } else if (division.equals("Montana")) {
            return 25;
        } else if (division.equals("Nebraska")) {
            return 26;
        } else if (division.equals("Nevada")) {
            return 27;
        } else if (division.equals("New Hampshire")) {
            return 28;
        } else if (division.equals("New Jersey")) {
            return 29;
        } else if (division.equals("New Mexico")) {
            return 30;
        } else if (division.equals("New York")) {
            return 31;
        } else if (division.equals("North Carolina")) {
            return 32;
        } else if (division.equals("North Dakota")) {
            return 33;
        } else if (division.equals("Ohio")) {
            return 34;
        } else if (division.equals("Oklahoma")) {
            return 35;
        } else if (division.equals("Oregon")) {
            return 36;
        } else if (division.equals("Pennsylvania")) {
            return 37;
        } else if (division.equals("Rhode Island")) {
            return 38;
        } else if (division.equals("South Carolina")) {
            return 39;
        } else if (division.equals("South Dakota")) {
            return 40;
        } else if (division.equals("Tennessee")) {
            return 41;
        } else if (division.equals("Texas")) {
            return 42;
        } else if (division.equals("Utah")) {
            return 43;
        } else if (division.equals("Vermont")) {
            return 44;
        } else if (division.equals("Virginia")) {
            return 45;
        } else if (division.equals("Washington")) {
            return 46;
        } else if (division.equals("West Virginia")) {
            return 47;
        } else if (division.equals("Wisconsin")) {
            return 48;
        } else if (division.equals("Wyoming")) {
            return 49;
        } else if (division.equals("Alaska")){
            return 54;
        } else if (division.equals("Hawaii")) {
            return 52;
        } else if (division.equals("Northwest Territories")) {
            return 60;
        } else if (division.equals("Alberta")) {
            return 61;
        } else if (division.equals("British Columbia")) {
            return 62;
        } else if (division.equals("Manitoba")) {
            return 63;
        } else if (division.equals("New Brunswick")) {
            return 64;
        } else if (division.equals("Nova Scotia")) {
            return 65;
        } else if (division.equals("Prince Edward Island")) {
            return 66;
        } else if (division.equals("Ontario")) {
            return 67;
        } else if (division.equals("Québec")) {
            return 68;
        } else if (division.equals("Saskatchewan")) {
            return 69;
        } else if (division.equals("Nunavut")) {
            return 70;
        } else if (division.equals("Yukon")) {
            return 71;
        } else if (division.equals("Newfoundland and Labrador")) {
            return 72;
        } else if (division.equals("England")) {
            return 101;
        } else if (division.equals("Wales")) {
            return 102;
        } else if (division.equals("Scotland")) {
            return 103;
        } else if (division.equals("Northern Ireland")) {
            return 104;
        } else {
            return -1;
        }
    }

    /**
     *
     * This method takes the Division_ID and returns the associated string
     *
     * @param divisionId
     * @return
     */
    public static String divisionStringRetriever(int divisionId) {

        switch (divisionId) {
            case 1:
                return "Alabama";
            case 2:
                return "Arizona";
            case 3:
                return "Arkansas";
            case 4:
                return "California";
            case 5:
                return "Colorado";
            case 6:
                return "Connecticut";
            case 7:
                return "Delaware";
            case 8:
                return "District of Columbia";
            case 9:
                return "Florida";
            case 10:
                return "Georgia";
            case 11:
                return "Idaho";
            case 12:
                return "Illinois";
            case 13:
                return "Indiana";
            case 14:
                return "Iowa";
            case 15:
                return "Kansas";
            case 16:
                return "Kentucky";
            case 17:
                return "Louisiana";
            case 18:
                return "Maine";
            case 19:
                return "Maryland";
            case 20:
                return "Massachusetts";
            case 21:
                return "Michigan";
            case 22:
                return "Minnesota";
            case 23:
                return "Mississippi";
            case 24:
                return "Missouri";
            case 25:
                return "Montana";
            case 26:
                return "Nebraska";
            case 27:
                return "Nevada";
            case 28:
                return "New Hampshire";
            case 29:
                return "New Jersey";
            case 30:
                return "New Mexico";
            case 31:
                return "New York";
            case 32:
                return "North Carolina";
            case 33:
                return "North Dakota";
            case 34:
                return "Ohio";
            case 35:
                return "Oklahoma";
            case 36:
                return "Oregon";
            case 37:
                return "Pennsylvania";
            case 38:
                return "Rhode Island";
            case 39:
                return "South Carolina";
            case 40:
                return "South Dakota";
            case 41:
                return "Tennessee";
            case 42:
                return "Texas";
            case 43:
                return "Utah";
            case 44:
                return "Vermont";
            case 45:
                return "Virginia";
            case 46:
                return "Washington";
            case 47:
                return "West Virginia";
            case 48:
                return "Wisconsin";
            case 49:
                return "Wyoming";
            case 54:
                return "Alaska";
            case 52:
                return "Hawaii";
            case 60:
                return "Northwest Territories";
            case 61:
                return "Alberta";
            case 62:
                return "British Columbia";
            case 63:
                return "Manitoba";
            case 64:
                return "New Brunswick";
            case 65:
                return "Nova Scotia";
            case 66:
                return "Prince Edward Island";
            case 67:
                return "Ontario";
            case 68:
                return "Québec";
            case 69:
                return "Saskatchewan";
            case 70:
                return "Nunavut";
            case 71:
                return "Yukon";
            case 72:
                return "Newfoundland and Labrador";
            case 101:
                return "England";
            case 102:
                return "Wales";
            case 103:
                return "Scotland";
            case 104:
                return "Northern Ireland";
            default:
                return "Invalid divisionId";

        }
    }
}


