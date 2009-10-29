/**
 *
 * Copyright 2006-2009 David Blevins, Andrew Bayer, Michael Vorburger
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.kenai.issuezilla2jira.translator;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;

import com.kenai.issuezilla2jira.parser.Attachment;

/**
 * Attachment Utils.
 */
public class AttachmentsUtil {

    public File getAttachmentsDir() {
        return attachmentsDir;
    }

    public void setAttachmentsDir(File attachmentsDir) {
        this.attachmentsDir = attachmentsDir;
    }

    private File attachmentsDir;

    private File saveAttachment(File dir, Attachment attachment) throws IOException {
        File file = getLocalFile(dir, attachment);

        if (!attachment.getData().equals("")) {
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            
            try {
                out.write(attachment.getData());
            } catch (IOException e) {
                out.close();
            }
        }
        else if (!attachment.getAttachUrl().equals("")) {
            FileOutputStream out = new FileOutputStream(file);
            
            URL url = new URL(attachment.getAttachUrl());
            InputStream in = url.openStream();
            try {
                in = new BufferedInputStream(in);
                int i = in.read();
                while (i != -1) {
                    out.write(i);
                    i = in.read();
                }
            } catch (IOException e) {
                out.close();
                in.close();
            }
        }
        
        return file;
    }
    
    public File saveAttachment(Attachment attachment) throws IOException {
        return saveAttachment(getOrCreateAttachmentsDir("attachments"), attachment);
    }

    
	public File getLocalFile(File dir, Attachment attachment) {
		File idDir = new File(dir, attachment.getAttachId() + "");
        idDir.mkdir();
        File file = new File(idDir, attachment.getFilename());
		return file;
	}
	
	public File getLocalFile(Attachment attachment) throws IOException {
		File file = getLocalFile(getOrCreateAttachmentsDir("attachments"), attachment);
		if (!file.exists()) 
			throw new IOException(file + " does not exist, run DownloadAttachments first..");
		return file;
	}

    private File getOrCreateAttachmentsDir(String prefix) throws IOException {
        if (attachmentsDir != null) {
            return attachmentsDir;
        } else {
        	throw new IOException("attachmentsDir has not been set");
        }
/*        
        if (System.getProperty("attachDir") != null) {
            return new File(System.getProperty("attachDir"));
        }
        File tempFile = File.createTempFile("foo", "bar");
        File tmpDir = tempFile.getParentFile();

        int suffix = 1;
        File dir = new File(tmpDir, prefix);
        while (dir.exists()) {
            dir = new File(tmpDir, prefix + (suffix++));
        }

        if (!dir.mkdir()) {
            throw new IOException("Could not create tmp attachments dir " + dir.getName());
        }

        attachmentsDir = dir;
        return attachmentsDir;
*/        
    }

}
