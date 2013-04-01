package com.northwoods.data;

public class DalConstants {
	public class Tables {
		public static final String REF_PROGRAM_TYPE = "RefProgramType";
		public static final String REF_EVENT_TYPE = "RefEventType";
		public static final String TRANS_CASE = "TransCase";
		public static final String TRANS_MEMBER = "TransMember";
		public static final String TRANS_DOCUMENT = "TransDocument";
		public static final String REF_DOCUMENT_TYPE = "RefDocumentType";

		public static final String JOIN_REF_EVENT_TYPE_AUDIO_DOCUMENT_TYPE = "JoinRefEventTypeAudioDocumentType";
		public static final String JOIN_REF_EVENT_TYPE_CAPTURE_DOCUMENT_TYPE = "JoinRefEventTypeCaptureDocumentType";

		public static final String EDITABLE_OBJECT = "EditableObject";

	}

	public static final String SPEED = "Speed";

	public static final String LONGITUDE = "Longitude";

	public static final String LATITUDE = "Latitude";

	public static final String EDIT_STATE = "EditState";

	public static final String BEARING = "Bearing";

	public static final String ALTITUDE = "Altitude";

	public static final String ACCURACY = "Accuracy";
	public static final String CREATE_DATE = "CreateDate";

	public static final String EQUALS = " = ";
	public static final String WHERE = " where ";
	public static final String AND = " and ";

	public static final String DOCUMENT_TYPE_NAME = "DocumentTypeName";
	public static final String PK_REF_DOCUMENT_TYPE = "pkRefDocumentType";

	public static final String PK_TRANS_DOCUMENT = "pkTransDocument";
	public static final String FK_TRANS_MEMBER = "fkTransMember";
	public static final String FK_REF_DOCUMENT_TYPE = "fkRefDocumentType";
	public static final String FK_TRANS_CASE = "fkTransCase";
	public static final String FK_EDITABLE_OBJECT = "fkEditableObject";
	public static final String FILE_PATH = "FilePath";

	public static final String PK_REF_EVENT_TYPE = "pkRefEventType";
	public static final String FK_REF_EVENT_TYPE = "fkRefEventType";
	public static final String EVENT_TYPE_NAME = "EventTypeName";

	public static final String FK_REF_PROGRAM_TYPE = "fkRefProgramType";
	public static final String FK_PROGRAM_TYPE = "fkProgramType";

	public static final String PK_TRANS_CASE = "pkTransCase";
	public static final String LOCAL_CASE_NUMBER = "LocalCaseNumber";
	public static final String STATE_CASE_NUMBER = "StateCaseNumber";

	public static final String CASE_MEMBERS_VIEW = "CaseMembersView";
	public static final String PK_TRANS_MEMBER = "pkTransMember";
	public static final String FIRST_NAME = "FirstName";
	public static final String MIDDLE_NAME = "MiddleName";
	public static final String LAST_NAME = "LastName";

	public static final String PK_REF_PROGRAM_TYPE = "pkRefProgramType";
	public static final String PK_EDITABLE_OBJECT = "pkEditableObject";
}
