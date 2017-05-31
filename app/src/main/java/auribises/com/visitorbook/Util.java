package auribises.com.visitorbook;

import android.net.Uri;

public class Util {
    public static final String sharedPreferences="prefs";
    public static final String loginSignal="loginSignal";

    // 1. Information for Vehicle Database
    public static final int DB_VERSIONVEHICLE = 1;
    public static final String DB_NAMEVEHICLE = "Vehicle.db";

    // Information for Vehicle Table
    public static final String TAB_NAMEVEHICLE = "Vehicle";
    public static final String COL_IDVEHICLE = "_ID";
    public static final String COL_NAMEVEHICLE = "NAME";
    public static final String COL_PHONEVEHICLE = "PHONE";
    public static final String COL_EMAILVEHICLE = "EMAIL";
    public static final String COL_GENDERVEHICLE = "GENDER";
    public static final String COL_VEHICLEVEHICLE = "VEHICLE";
    public static final String COL_VEHICLENUMBERVEHICLE = "VEHICLENUMBER";

    public static final String CREATE_TAB_QUERYVEHICLE = "create table Vehicle(" +
         "_ID integer primary key autoincrement," +
         "NAME varchar(256)," +
         "PHONE varchar(256)," +
         "EMAIL varchar(256)," +
         "GENDER varchar(256)," +
         "VEHICLE varchar(256)," +
         "VEHICLENUMBER varchar(256)" +
         ")";

    // URI
    public static final Uri VEHICLE_URI = Uri.parse("content://auribises.com.Vehicle.vehicleprovider/"+TAB_NAMEVEHICLE);

    // URL
    public static final String INSERT_VEHICLE_PHP = "http://tajinderj.esy.es/Vehicle/insert.php";
    public static final String RETRIEVE_VEHICLE_PHP = "http://tajinderj.esy.es/Vehicle/retrieve.php";
    public static final String DELETE_VEHICLE_PHP = "http://tajinderj.esy.es/Vehicle/delete.php";
    public static final String UPDATE_VEHICLE_PHP = "http://tajinderj.esy.es/Vehicle/update.php";


    // 2. Information for Teacher Database
    public static final int DB_VERSIONTEACHER = 1;
    public static final String DB_NAMETEACHER = "Teacher.db";

    // Information for Teacher Table
    public static final String TAB_NAMETEACHER = "Teacher";
    public static final String COL_IDTEACHER = "_ID";
    public static final String COL_NAMETEACHER = "NAME";
    public static final String COL_PHONETEACHER = "PHONE";
    public static final String COL_EMAILTEACHER = "EMAIL";
    public static final String COL_GENDERTEACHER = "GENDER";
    public static final String COL_ADDRESSTEACHER = "ADDRESS";
    public static final String COL_PURPOSETEACHER = "PURPOSE";
    public static final String COL_DATETEACHER = "DATE";
    public static final String COL_TIMETEACHER = "TIME";
    public static final String COL_ROOMTEACHER = "ROOM";

    public static final String CREATE_TAB_QUERYTEACHER = "create table Teacher(" +
            "_ID integer primary key autoincrement," +
            "NAME varchar(256)," +
            "PHONE varchar(256)," +
            "EMAIL varchar(256)," +
            "GENDER varchar(256)," +
            "ADDRESS varchar(256)," +
            "PURPOSE varchar(256)," +
            "DATE varchar(256)," +
            "TIME varchar(256)," +
            "ROOM varchar(256)" +
            ")";

    // URI
    public static final Uri TEACHER_URI = Uri.parse("content://auribises.com.Teacher.teacherprovider/"+TAB_NAMETEACHER);

    // URL
    public static final String INSERT_TEACHER_PHP = "http://tajinderj.esy.es/Teacher/insert.php";
    public static final String RETRIEVE_TEACHER_PHP = "http://tajinderj.esy.es/Teacher/retrieve.php";
    public static final String DELETE_TEACHER_PHP = "http://tajinderj.esy.es/Teacher/delete.php";
    public static final String UPDATE_TEACHER_PHP = "http://tajinderj.esy.es/Teacher/update.php";

    // 3. Information for Teacherlogin Database
    public static final int DB_VERSIONTEACHERLOGIN = 1;
    public static final String DB_NAMETEACHERLOGIN = "teacherlogin.db";

    // Information for Teacherlogin Table
    public static final String TAB_NAMETEACHERLOGIN = "teacherlogin";
    public static final String COL_IDTEACHERLOGIN = "_ID";
    public static final String COL_USERNAMETEACHERLOGIN = "USERNAME";
    public static final String COL_PASSWORDTEACHERLOGIN = "PASSWORD";

    public static final String CREATE_TAB_QUERYTEACHERLOGIN = "create table teacherlogin(" +
            "_ID integer primary key autoincrement," +
            "USERNAME varchar(256)," +
            "PASSWORD varchar(256)" +
            ")";

    // URI
    public static final Uri TEACHERLOGIN_URI = Uri.parse("content://auribises.com.Teacherlogin.teacherloginprovider/"+TAB_NAMETEACHERLOGIN);

    // URL
    public static final String TEACHERLOGIN_PHP = "http://tajinderj.esy.es/Teacherlogin/teacherlogin.php";

    // 4. Information for Visitorentry Database
    public static final int DB_VERSIONVISITOR1 = 1;
    public static final String DB_NAMEVISITOR1 = "visitorentry.db";

    // Information for visitorentry Table
      public static final String TAB_NAMEVISITOR1 = "visitorentry";
//    public static final String COL_IDVISITOR = "_ID";
//    public static final String COL_NAMEVISITOR = "NAME";
//    public static final String COL_PHONEVISITOR = "PHONE";
//    public static final String COL_EMAILVISITOR = "EMAIL";
//    public static final String COL_GENDERVISITOR = "GENDER";
//    public static final String COL_ADDRESSVISITOR = "ADDRESS";
//    public static final String COL_PURPOSEVISITOR = "PURPOSE";
//    public static final String COL_DATEVISITOR = "DATE";
//    public static final String COL_TIMEVISITOR = "TIME";
      public static final String COL_TEACHERVISITOR = "TEACHER";
      public static final String COL_BRANCHVISITOR = "BRANCH";
//    public static final String COL_IDPROOFVISITOR = "IDPROOF";
//    public static final String COL_IDPROOFNUBERVISITOR = "IDPROOFNUMBER";
//    public static final String COL_VEHICLEVISITOR = "VEHICLE";
//    public static final String COL_VEHICLENUMBERVISITOR = "VEHICLENUMBER";

    public static final String CREATE_TAB_QUERYVISITORENTRY = "create table visitorentry(" +
            "_ID integer primary key autoincrement," +
            "NAME varchar(256)," +
            "PHONE varchar(256)," +
            "EMAIL varchar(256)," +
            "GENDER varchar(256)," +
            "ADDRESS varchar(256)," +
            "PURPOSE varchar(256)," +
            "DATE varchar(256)," +
            "TIME varchar(256)," +
            "TEACHER varchar(256)," +
            "BRANCH varchar(256)," +
            "IDPROOF varchar(256)," +
            "IDPROOFNUMBER varchar(256)," +
            "VEHICLE varchar(256)," +
            "VEHICLENUMBER varchar(256)" +
            ")";

//    public static final String PREFS_NAME = "visitorbook";
//    public static final String KEY_NAME = "keyName";
//    public static final String KEY_PHONE = "keyPhone";
//    public static final String KEY_EMAIL = "keyEmail";
//    public static final String KEY_GENDER = "keyGender";
//    public static final String KEY_ADDRESS = "keyAddress";
//    public static final String KEY_PURPOSE = "keyPurpose";
//    public static final String KEY_DATE = "keyDate";
//    public static final String KEY_TIME = "keyTime";
      public static final String KEY_TEACHER = "keyTeacher";
      public static final String KEY_BRANCH = "keyBranch";
//    public static final String KEY_IDPROOF = "keyIDProof";
//    public static final String KEY_IDPROOFNUMBER = "keyIDProofNumber";
//    public static final String KEY_VEHICLE = "keyVehicle";
//    public static final String KEY_VEHICLENUMBER = "keyVehicleNumber";

    // URI
    public static final Uri VISITORENTRY_URI = Uri.parse("content://auribises.com.auribises.visitorentry.visitorentryprovider/"+TAB_NAMEVISITOR1);

    final static String URI = "http://tajinderj.esy.es/visitorentry/";

    // URL
    public static final String INSERT_VISITORENTRY_PHP = "http://tajinderj.esy.es/visitorentry/insert.php";
    public static final String RETRIEVE_VISITORENTRY_PHP = "http://tajinderj.esy.es/visitorentry/retrieve.php";
    public static final String DELETE_VISITORENTRY_PHP = "http://tajinderj.esy.es/visitorentry/delete.php";
    public static final String UPDATE_VISITORENTRY_PHP = "http://tajinderj.esy.es/visitorentry/update.php";

    // 5. Information for Adminentry Database
    public static final int DB_VERSIONVISITOR = 1;
    public static final String DB_NAMEVISITOR = "adminentry.db";

    // Information for visitorentry Table
    public static final String TAB_NAMEVISITOR = "adminentry";
    public static final String COL_IDVISITOR = "_ID";
    public static final String COL_NAMEVISITOR = "NAME";
    public static final String COL_PHONEVISITOR = "PHONE";
    public static final String COL_EMAILVISITOR = "EMAIL";
    public static final String COL_GENDERVISITOR = "GENDER";
    public static final String COL_ADDRESSVISITOR = "ADDRESS";
    public static final String COL_PURPOSEVISITOR = "PURPOSE";
    public static final String COL_DATEVISITOR = "DATE";
    public static final String COL_TIMEVISITOR = "TIME";
    public static final String COL_ADMINVISITOR = "ADMIN";
    public static final String COL_IDPROOFVISITOR = "IDPROOF";
    public static final String COL_IDPROOFNUBERVISITOR = "IDPROOFNUMBER";
    public static final String COL_VEHICLEVISITOR = "VEHICLE";
    public static final String COL_VEHICLENUMBERVISITOR = "VEHICLENUMBER";

    public static final String CREATE_TAB_QUERYADMINENTRY = "create table adminentry(" +
            "_ID integer primary key autoincrement," +
            "NAME varchar(256)," +
            "PHONE varchar(256)," +
            "EMAIL varchar(256)," +
            "GENDER varchar(256)," +
            "ADDRESS varchar(256)," +
            "PURPOSE varchar(256)," +
            "DATE varchar(256)," +
            "TIME varchar(256)," +
            "ADMIN varchar(256)," +
            "IDPROOF varchar(256)," +
            "IDPROOFNUMBER varchar(256)," +
            "VEHICLE varchar(256)," +
            "VEHICLENUMBER varchar(256)" +
            ")";

    public static final String PREFS_NAME = "visitorbook";
    public static final String KEY_NAME = "keyName";
    public static final String KEY_PHONE = "keyPhone";
    public static final String KEY_EMAIL = "keyEmail";
    public static final String KEY_GENDER = "keyGender";
    public static final String KEY_ADDRESS = "keyAddress";
    public static final String KEY_PURPOSE = "keyPurpose";
    public static final String KEY_DATE = "keyDate";
    public static final String KEY_TIME = "keyTime";
    public static final String KEY_ADMIN = "keyAdmin";
    public static final String KEY_IDPROOF = "keyIDProof";
    public static final String KEY_IDPROOFNUMBER = "keyIDProofNumber";
    public static final String KEY_VEHICLE = "keyVehicle";
    public static final String KEY_VEHICLENUMBER = "keyVehicleNumber";

    // URI
    public static final Uri ADMINENTRY_URI = Uri.parse("content://auribises.com.auribises.adminentry.adminentryprovider/"+TAB_NAMEVISITOR);

    final static String URI1 = "http://tajinderj.esy.es/adminentry/";

    // URL
    public static final String INSERT_ADMINENTRY_PHP = "http://tajinderj.esy.es/adminentry/insert.php";
    public static final String RETRIEVE_ADMINENTRY_PHP = "http://tajinderj.esy.es/adminentry/retrieve.php";
    public static final String DELETE_ADMINENTRY_PHP = "http://tajinderj.esy.es/adminentry/delete.php";
    public static final String UPDATE_ADMINENTRY_PHP = "http://tajinderj.esy.es/adminentry/update.php";

    // 6. Information for Adminlogin Database
    public static final int DB_VERSIONADMIN = 1;
    public static final String DB_NAMEADMIN = "adminlogin.db";

    // Information for AdminLogin Table
    public static final String TAB_NAMEADMINLOGIN = "adminlogin";
    public static final String COL_IDADMINLOGIN = "_ID";
    public static final String COL_USERNAMEADMINLOGIN = "USERNAME";
    public static final String COL_PASSWORDADMINLOGIN = "PASSWORD";

    public static final String CREATE_TAB_QUERYADMINLOGIN = "create table adminlogin(" +
            "_ID integer primary key autoincrement," +
            "USERNAME varchar(256)," +
            "PASSWORD varchar(256)" +
            ")";

    // URI
    public static final Uri ADMINLOGIN_URI = Uri.parse("content://com.auribises.Adminlogin.adminloginprovider/"+TAB_NAMEADMINLOGIN);

    final static String URIADMIN = "http://tajinderj.esy.es/Adminlogin/";

    // URL
    public static final String ADMINLOGIN_PHP = "http://tajinderj.esy.es/Adminlogin/adminlogin.php";

    // 7. Information for Guardlogin Database
    public static final int DB_VERSIONGUARD = 1;
    public static final String DB_NAMEGUARD = "guardlogin.db";

    // Information for GuardLogin Table
    public static final String TAB_NAMEGUARDLOGIN = "guardlogin";
    public static final String COL_IDGUARDLOGIN = "_ID";
    public static final String COL_USERNAMEGUARDLOGIN = "USERNAME";
    public static final String COL_PASSWORDGUARDLOGIN = "PASSWORD";

    public static final String CREATE_TAB_QUERYGUARDLOGIN = "create table guardlogin(" +
            "_ID integer primary key autoincrement," +
            "USERNAME varchar(256)," +
            "PASSWORD varchar(256)" +
            ")";

    // URI
    public static final Uri GUARDLOGIN_URI = Uri.parse("content://com.auribises.Adminappointment.teacherprovider/"+TAB_NAMEGUARDLOGIN);

    final static String URIGUARD = "http://tajinderj.esy.es/Guardlogin/";

    // URL
    public static final String GUARDLOGIN_PHP = "http://tajinderj.esy.es/Guardlogin/guardlogin.php";

    // 8. Information for RegisterAdmin Database
    public static final int DB_VERSIONREGISTERADMIN = 1;
    public static final String DB_NAMEREGISTERADMIN = "registeradmin.db";

    // Information for RegisterAdmin Table
    public static final String TAB_NAMEREGISTERADMIN = "registeradmin";
    public static final String COL_IDREGISTERADMIN = "_ID";
    public static final String COL_NAMEREGISTERADMIN = "NAME";
    public static final String COL_PHONEREGISTERADMIN = "PHONE";
    public static final String COL_EMAILREGISTERADMIN = "EMAIL";
    public static final String COL_BIRTHDATEREGISTERADMIN = "BIRTHDATE";
    public static final String COL_GENDERREGISTERADMIN = "GENDER";
    public static final String COL_ADDRESSREGISTERADMIN = "ADDRESS";
    public static final String COL_QUALIFICATIONREGISTERADMIN = "QUALIFICATION";
    public static final String COL_EXPERIENCEREGISTERADMIN = "EXPERIENCE";
    public static final String COL_PASSWORDREGISTERADMIN = "PASSWORD";

    public static final String CREATE_TAB_QUERYREGISTERADMIN = "create table registeradmin(" +
            "_ID integer primary key autoincrement," +
            "NAME varchar(256)," +
            "PHONE varchar(256)," +
            "EMAIL varchar(256)," +
            "BIRTHDATE varchar(256)," +
            "GENDER varchar(256)," +
            "ADDRESS varchar(256)," +
            "QUALIFICATION varchar(256)," +
            "EXPERIENCE varchar(256)," +
            "PASSWORD varchar(256)" +
            ")";

    // URI
    public static final Uri REGISTERADMIN_URI = Uri.parse("content://com.auribises.RegisterAdmin.registeradminprovider/"+TAB_NAMEREGISTERADMIN);

    // URL
    public static final String INSERT_REGISTERADMIN_PHP = "http://tajinderj.esy.es/registeradmin/insert.php";
    public static final String RETRIEVE_REGISTERADMIN_PHP = "http://tajinderj.esy.es/registeradmin/retrieve.php";
    public static final String DELETE_REGISTERADMIN_PHP = "http://tajinderj.esy.es/registeradmin/delete.php";
    public static final String UPDATE_REGISTERADMIN_PHP = "http://tajinderj.esy.es/registeradmin/update.php";

    // 9. Information for RegisterGuard Database
    public static final int DB_VERSIONREGISTERGUARD = 1;
    public static final String DB_NAMEREGISTERGUARD = "registerguard.db";

    // Information for RegisterGuard Table
    public static final String TAB_NAMEREGISTERGUARD = "registerguard";
    public static final String COL_IDREGISTERGUARD = "_ID";
    public static final String COL_NAMEREGISTERGUARD = "NAME";
    public static final String COL_PHONEREGISTERGUARD = "PHONE";
    public static final String COL_EMAILREGISTERGUARD = "EMAIL";
    public static final String COL_BIRTHDATEREGISTERGUARD = "BIRTHDATE";
    public static final String COL_GENDERREGISTERGUARD = "GENDER";
    public static final String COL_ADDRESSREGISTERGUARD = "ADDRESS";
    public static final String COL_QUALIFICATIONREGISTERGUARD = "QUALIFICATION";
    public static final String COL_EXPERIENCEREGISTERGUARD = "EXPERIENCE";
    public static final String COL_PASSWORDREGISTERGUARD = "PASSWORD";

    public static final String CREATE_TAB_QUERYREGISTERGUARD = "create table registerguard(" +
            "_ID integer primary key autoincrement," +
            "NAME varchar(256)," +
            "PHONE varchar(256)," +
            "EMAIL varchar(256)," +
            "BIRTHDATE varchar(256)," +
            "GENDER varchar(256)," +
            "ADDRESS varchar(256)," +
            "QUALIFICATION varchar(256)," +
            "EXPERIENCE varchar(256)," +
            "PASSWORD varchar(256)" +
            ")";

    // URI
    public static final Uri REGISTERGUARD_URI = Uri.parse("content://com.auribises.RegisterGuard.registerguardprovider/"+TAB_NAMEREGISTERGUARD);

    // URL
    public static final String INSERT_REGISTERGUARD_PHP = "http://tajinderj.esy.es/registerguard/insert.php";
    public static final String RETRIEVE_REGISTERGUARD_PHP = "http://tajinderj.esy.es/registerguard/retrieve.php";
    public static final String DELETE_REGISTERGUARD_PHP = "http://tajinderj.esy.es/registerguard/delete.php";
    public static final String UPDATE_REGISTERGUARD_PHP = "http://tajinderj.esy.es/registerguard/update.php";

    // 10. Information for RegisterTeacher Database
    public static final int DB_VERSIONREGISTERTEACHER = 1;
    public static final String DB_NAMEREGISTERTEACHER = "registerteacher.db";

    // Information for RegisterTeacher Table
    public static final String TAB_NAMEREGISTERTEACHER = "registerteacher";
    public static final String COL_IDREGISTERTEACHER = "_ID";
    public static final String COL_NAMEREGISTERTEACHER = "NAME";
    public static final String COL_PHONEREGISTERTEACHER = "PHONE";
    public static final String COL_EMAILREGISTERTEACHER = "EMAIL";
    public static final String COL_BIRTHDATEREGISTERTEACHER = "BIRTHDATE";
    public static final String COL_GENDERREGISTERTEACHER = "GENDER";
    public static final String COL_ADDRESSREGISTERTEACHER = "ADDRESS";
    public static final String COL_QUALIFICATIONREGISTERTEACHER = "QUALIFICATION";
    public static final String COL_EXPERIENCEREGISTERTEACHER = "EXPERIENCE";
    public static final String COL_PASSWORDREGISTERTEACHER = "PASSWORD";

    public static final String CREATE_TAB_QUERYREGISTERTEACHER = "create table registerteacher(" +
            "_ID integer primary key autoincrement," +
            "NAME varchar(256)," +
            "PHONE varchar(256)," +
            "EMAIL varchar(256)," +
            "BIRTHDATE varchar(256)," +
            "GENDER varchar(256)," +
            "ADDRESS varchar(256)," +
            "QUALIFICATION varchar(256)," +
            "EXPERIENCE varchar(256)," +
            "PASSWORD varchar(256)" +
            ")";

    // REGISTER TEACHER
    public static final String KEY_NAMEREGISTERTEACHER = "keyName";
    public static final String KEY_PHONEREGISTERTEACHER = "keyPhone";
    public static final String KEY_EMAILREGISTERTEACHER = "keyEmail";
    public static final String KEY_BIRTHDATEREGISTERTEACHER = "keyBirthdate";
    public static final String KEY_GENDERREGISTERTEACHER = "keyGender";
    public static final String KEY_ADDRESSREGISTERTEACHER = "keyAddress";
    public static final String KEY_QUALIFICATIONREGISTERTEACHER = "keyQualification";
    public static final String KEY_EXPERIENCEREGISTERTEACHER = "keyExperience";
    public static final String KEY_PASSWORDREGISTERTEACHER = "keyPassword";

    // URI
    public static final Uri REGISTERTEACHER_URI = Uri.parse("content://com.auribises.RegisterTeacher.registerteacherprovider/"+TAB_NAMEREGISTERTEACHER);

    // URL
    public static final String INSERT_REGISTERTEACHER_PHP = "http://tajinderj.esy.es/registerteacher/insert.php";
    public static final String RETRIEVE_REGISTERTEACHER_PHP = "http://tajinderj.esy.es/registerteacher/retrieve.php";
    public static final String DELETE_REGISTERTEACHER_PHP = "http://tajinderj.esy.es/registerteacher/delete.php";
    public static final String UPDATE_REGISTERTEACHER_PHP = "http://tajinderj.esy.es/registerteacher/update.php";

   // 11. Information for Adminappointment Database
   public static final int DB_VERSIONADMINAPPOINTMENT = 1;
   public static final String DB_NAMEADMINAPPOINTMENT = "adminappointment.db";

    // Information for Adminappointment Table
    public static final String TAB_NAMEADMIN = "adminappointment";
    public static final String COL_IDADMIN = "_ID";
    public static final String COL_NAMEADMIN = "NAME";
    public static final String COL_PHONEADMIN = "PHONE";
    public static final String COL_EMAILADMIN = "EMAIL";
    public static final String COL_GENDERADMIN = "GENDER";
    public static final String COL_ADDRESSADMIN = "ADDRESS";
    public static final String COL_PURPOSEADMIN = "PURPOSE";
    public static final String COL_DATEADMIN = "DATE";
    public static final String COL_TIMEADMIN = "TIME";
    public static final String COL_ROOMADMIN = "ROOM";

    public static final String CREATE_TAB_QUERYADMIN = "create table adminappointment(" +
            "_ID integer primary key autoincrement," +
            "NAME varchar(256)," +
            "PHONE varchar(256)," +
            "EMAIL varchar(256)," +
            "GENDER varchar(256)," +
            "ADDRESS varchar(256)," +
            "PURPOSE varchar(256)," +
            "DATE varchar(256)," +
            "TIME varchar(256)," +
            "ROOM varchar(256)" +
            ")";

    // URI
    public static final Uri ADMIN_APPOINTMENT_URI = Uri.parse("content://auribises.com.Adminappointment.adminappointmentprovider/"+TAB_NAMEADMIN);

    // URL
    public static final String INSERT_ADMIN_APPOINTMENT_PHP = "http://tajinderj.esy.es/adminappointment/insert.php";
    public static final String RETRIEVE_ADMIN_APPOINTMENT_PHP = "http://tajinderj.esy.es/adminappointment/retrieve.php";
    public static final String DELETE_ADMIN_APPOINTMENT_PHP = "http://tajinderj.esy.es/adminappointment/delete.php";
    public static final String UPDATE_ADMIN_APPOINTMENT_PHP = "http://tajinderj.esy.es/adminappointment/update.php";

    public static final int REQCODE= 101;
    public static final int UPREQCODE= 103;
    public static final int UPRESCODE= 201;

    public static final String keyUpdate= "update";
    public static final String keyresult = "result";

    public static final String GUARD_FORGETPASSWORD_PHP = "http://tajinderj.esy.es/Guardforgetpassword/ForgetPassword.php";

    public static final String TEACHER_FORGETPASSWORD_PHP = "http://tajinderj.esy.es/Teacherforgetpassword/ForgetPassword.php";

    public static final String ADMIN_FORGETPASSWORD_PHP = "http://tajinderj.esy.es/Adminforgetpassword/ForgetPassword.php";

    // URL
    public static final String GUARDCHANGEPASSWORD_PHP = "http://tajinderj.esy.es/Guardchangepassword/guardchange.php";

    public static final String TEACHERCHANGEPASSWORD_PHP = "http://tajinderj.esy.es/Teacherchangepassword/teacherchange.php";

    public static final String ADMINCHANGEPASSWORD_PHP = "http://tajinderj.esy.es/Adminchangepassword/adminchange.php";
}










