package synapticloop.b2;

/**
 * The action for an associated file, either 'hide' or 'upload', "upload" means 
 * a file that was uploaded to B2 Cloud Storage. "hide" means a file version 
 * marking the file as hidden, so that it will not show up in b2_list_file_names.
 * 
 * The result of b2_list_file_names will contain only "upload". The result of 
 * b2_list_file_versions may have both.
 * 
 * @author synapticloop
 */
public enum Action {
	hide,
	upload
}
