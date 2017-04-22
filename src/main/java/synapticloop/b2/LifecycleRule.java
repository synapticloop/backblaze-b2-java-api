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

import org.json.JSONObject;

/**
 * <p>Lifecycle rules instruct the B2 service to automatically hide and/or delete 
 * old files. You can set up rules to do things like delete old versions of 
 * files 30 days after a newer version was uploaded.</p>
 * 
 * <p>A bucket can have up to 100 lifecycle rules. Each rule has a fileNamePrefix 
 * that specifies which files in the bucket it applies to. Any file whose name 
 * starts with the prefix is subject to the rule. A prefix of the empty string, 
 * "", means that the rule applies to all files in the bucket.</p>
 * 
 * <p>You're not allowed to create two rules that both apply to the same files. 
 * For example, a rule with a file name prefix of photos/ and a rule with a file 
 * name prefix of photos/kittens/ would both apply to a file named 
 * photos/kittens/fluffy.jpg, so you're not allowed to have both rules at the 
 * same time.</p>
 * 
 * <p>Each lifecycle rule specifies two things:</p>
 *  
 * <ul>
 * 	<li>daysFromUploadingToHiding and</li> 
 * 	<li>daysFromHidingToDeleting.</li>
 * </ul>
 * 
 * <p>Either can be null, which means that part of the rule doesn't apply. Setting 
 * both to null is not allowed, because the rule would do nothing.</p>
 * 
 * <p>Setting daysFromUploadingToHiding to 0 is not allowed. When set, it must be a 
 * positive number.</p>
 * 
 * <p>The most commonly used setting is daysFromHidingToDeleting, which says how long 
 * to keep file versions that are not the current version. A file version counts 
 * as hidden when explicitly hidden with b2_hide_file, or when a newer file with 
 * the same name is uploaded. When a rule with this setting applies, the file will 
 * be deleted the given number of days after it is hidden.</p>
 * 
 * <p>For example, if you are backing up your files to B2 using the B2 command-line 
 * tool, or another software package that uploads files when they change, B2 will 
 * keep old versions of the file. This is very helpful, because it means the old 
 * versions are there if the original file is accidentally deleted. On the other 
 * hand, keeping them forever can clutter things up. For an application like this, 
 * you might want a lifecycle rule that keeps old versions in the backup/ folder 
 * for 30 days, and then deletes them:</p>
 * 
 * <pre>
    {
        "daysFromHidingToDeleting": 30,
        "daysFromUploadingToHiding": null,
        "fileNamePrefix": "backup/"
    }
 * </pre>
 * 
 * <p>The daysFromUploadingToHiding setting is less frequently used. It causes files to 
 * be hidden automatically after the given number of days. This can be useful for things 
 * like server log files that can be deleted after a while. This rule will keep files 
 * in the logs/ folder for 7 days, and then hide and immediately delete them:</p>
 * 
 * <pre>
    {
        "daysFromHidingToDeleting": 0,
        "daysFromUploadingToHiding": 7,
        "fileNamePrefix": "logs/"
    }
 * </pre>
 * 
 * <p>The API calls related to lifecycle rules are:</p>
 * 
 * <ul>
 * 	<li>b2_create_bucket - creates a new bucket</li>
 * 	<li>b2_update_bucket - changes the settings on a bucket</li>
 * </ul>
 * 
 * <p>When you create a new bucket, you can specify the lifecycle rules. Later, 
 * you can change the rules on a bucket by updating it.</p>
 * 
 * @author synapticloop
 *
 */
public class LifecycleRule {
	private final Long daysFromHidingToDeleting;
	private final Long daysFromUploadingToHiding;
	private final String fileNamePrefix;

	public LifecycleRule(Long daysFromHidingToDeleting, Long daysFromUploadingToHiding, String fileNamePrefix) {
		this.daysFromHidingToDeleting = daysFromHidingToDeleting;
		this.daysFromUploadingToHiding = daysFromUploadingToHiding;
		this.fileNamePrefix = fileNamePrefix;
	}

	public LifecycleRule(JSONObject jsonObject) {
		long hide = jsonObject.optLong("daysFromHidingToDeleting", Long.MIN_VALUE);
		if(hide == Long.MIN_VALUE) {
			daysFromHidingToDeleting = null;
		} else {
			daysFromHidingToDeleting = hide;
		}

		long upload = jsonObject.optLong("daysFromUploadingToHiding", Long.MIN_VALUE);
		if(upload == Long.MIN_VALUE) {
			daysFromUploadingToHiding = null;
		} else {
			daysFromUploadingToHiding = upload;
		}

		fileNamePrefix = jsonObject.optString("fileNamePrefix", null);
	}

	/**
	 * The number of days from when the file was hidden to when it will be deleted
	 * 
	 * @return the number of days from hiding to deleting
	 */
	public Long getDaysFromHidingToDeleting() { return this.daysFromHidingToDeleting; }

	/**
	 * The number of days from when the file was uploaded to when it will be deleted
	 * 
	 * @return the number of days from uploading to deleting
	 */
	public Long getDaysFromUploadingToHiding() { return this.daysFromUploadingToHiding; }

	/**
	 * The file name prefix to which this rule applies
	 * 
	 * @return the file name prefix to which this rule applies
	 */
	public String getFileNamePrefix() { return this.fileNamePrefix; }

}
