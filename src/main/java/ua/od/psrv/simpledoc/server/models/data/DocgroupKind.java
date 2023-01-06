package ua.od.psrv.simpledoc.server.models.data;

/**
 * Варианты документов: входящие, письма граждан, исходящие
 */
public enum DocgroupKind {

	/**
	 * Входящие
	 */
	In,

	/**
	 * Письма граждан
	 */
	Let,

	/**
	 * Исходящие/внутрение
	 */
	Out
}
