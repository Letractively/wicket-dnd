/*
 * Copyright 2009 Sven Meier
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wicketdnd;

import org.apache.wicket.Component;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;

/**
 * A drag and drop transfer.
 * 
 * @author Sven Meier
 */
public class Transfer
{

	public static final ResourceReference JS = new JavascriptResourceReference(Transfer.class,
			"wicket-dnd.js");

	/**
	 * Undefined CSS selector.
	 */
	public static final String UNDEFINED = "undefined";

	/**
	 * No operation.
	 */
	public static final int NONE = 0;

	/**
	 * Move operation.
	 */
	public static final int MOVE = 1;

	/**
	 * Copy operation.
	 */
	public static final int COPY = 2;

	/**
	 * Link operation.
	 */
	public static final int LINK = 4;

	/**
	 * Transfer type indicating any type supported.
	 * 
	 * @see DragSource#getTransferTypes()
	 * @see DropTarget#getTransferTypes()
	 */
	public static final String ANY = "";

	private String type;

	private int operation;

	private Object data;

	Transfer(String type, int operation)
	{
		this.type = type;
		this.operation = operation;
	}

	/**
	 * Get type of transfer.
	 * 
	 * @return type
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * Get the operation of this transfer.
	 * 
	 * @see DragSource#getOperations()
	 * @see DropTarget#getOperations()
	 * @see #MOVE
	 * @see #COPY
	 * @see #LINK
	 */
	public int getOperation()
	{
		return operation;
	}

	/**
	 * Set the data of this transfer.
	 * 
	 * @see DragSource#beforeDrop(Component, Transfer)
	 */
	public void setData(Object data)
	{
		this.data = data;
	}

	/**
	 * Get the data of this transfer.
	 * 
	 * @see DropTarget#onDrop(AjaxRequestTarget, Transfer, Location)
	 * @see DragSource#afterDrop(AjaxRequestTarget, Transfer)
	 */
	@SuppressWarnings("unchecked")
	public <T> T getData()
	{
		return (T)this.data;
	}

	/**
	 * Reject this transfer.
	 */
	public void reject()
	{
		throw new Reject();
	}
}