package ua.od.psrv.simpledoc.server.models.data;

/**
 * Варианты корреспондентов
 */
public enum CorrespondentKind {

	/**
	 * Входящий от организации
	 */
	IncomingOrganization,

	/**
	 * Входящий от гражданина
	 */
	IncomingCitizen,

	/**
	 * Сопроводительный
	 */
	CoverLetter,

	/**
	 * Исходящий к организации
	 */
	OutgoingOrganization,

	/**
	 * Исходящий к гражданину
	 */
	OutgoingCitizen
}
