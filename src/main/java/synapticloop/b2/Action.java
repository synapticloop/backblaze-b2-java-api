package synapticloop.b2;

/*
 * Copyright (c) 2016 - 2017 Synapticloop.
 * 
 * All rights reserved.
 * 
 * This code may contain contributions from other parties which, where 
 * applicable, will be listed in the default build file for the project 
 * ~and/or~ in a file named CONTRIBUTORS.txt in the root of the project.
 * 
 * This source code and any derived binaries are covered by the terms and 
 * conditions of the Licence agreement ("the Licence").  You may not use this 
 * source code or any derived binaries except in compliance with the Licence.  
 * A copy of the Licence is available in the file named LICENSE.txt shipped with 
 * this source code or binaries.
 */

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
	/**
	 * "hide" means a file version marking the file as hidden, so that it will not show up in b2_list_file_names.
	 */
	hide,
	/**
	 * Pending multipart upload. "start" means that a large file has been started, but not finished or canceled.
	 */
	upload,
	start
}
