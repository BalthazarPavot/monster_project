package user;

public class Project {

	private String name = "untitled" ;
	private Boolean WriteUser = false ;
	private Boolean WriteGroup = false ;
	private Boolean WriteOther = false ;
	private Boolean ReadUser = false ;
	private Boolean ReadGroup = false ;
	private Boolean ReadOther = false ;
	private Boolean loaded = false ;

	public Boolean isLoaded () {
		return loaded ;
	}

}
