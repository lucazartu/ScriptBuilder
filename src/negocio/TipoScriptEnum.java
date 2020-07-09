package negocio;

public enum TipoScriptEnum {
	INSERTUPDATE, DELETE;

	@Override
	public String toString() {
		switch (this) {
		case INSERTUPDATE:
			return "INSERT/UPDATE";
		case DELETE:
			return "DELETE";
		}
		
		return null;
	}
}
