package com.example.chatapp.sql;

import android.provider.BaseColumns;

/**
 * Created by delaroy on 5/11/17.
 */
 class BeneficiaryContract {

     static final class BeneficiaryEntry implements BaseColumns {

        static final String TABLE_NAME = "beneficiary";
         static final String COLUMN_BENEFICIARY_NAME = "beneficiary_name";
         static final String COLUMN_BENEFICIARY_EMAIL = "beneficiary_email";
         static final String COLUMN_BENEFICIARY_ADDRESS = "beneficiary_address";
         static final String COLUMN_BENEFICIARY_COUNTRY = "beneficiary_country";
         static final String COLUMN_BENEFICIARY_AGE = "beneficiary_age";
         static final String COLUMN_BENEFICIARY_GENDER = "beneficiary_gender";
         static final String COLUMN_BENEFICIARY_PULSERATE = "beneficiary_pulserate";
         static final String COLUMN_BENEFICIARY_WEIGHT = "beneficiary_weight";
         static final String COLUMN_BENEFICIARY_HEIGHT = "beneficiary_height";
         static final String COLUMN_BENEFICIARY_BLOODGRP = "beneficiary_bloodgrp";
    }
}