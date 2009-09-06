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
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Request;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.behavior.IBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.protocol.http.PageExpiredException;
import org.wicketstuff.prototype.PrototypeResourceReference;

import wicketdnd.util.MarkupIdVisitor;
import wicketdnd.util.StringArrayFormattable;

/**
 * A source of drags.
 * 
 * @see #getTransferTypes()
 * @see #beforeDrop(Request, Transfer)
 * @see #afterDrop(AjaxRequestTarget, Transfer)
 * 
 * @author Sven Meier
 */
public class DragSource extends AbstractBehavior
{

	private static final long serialVersionUID = 1L;

	private Component component;

	private String selector = Transfer.UNDEFINED;

	private String initiateSelector = Transfer.UNDEFINED;

	private int operations;

	/**
	 * Create a source of drag operations.
	 */
	public DragSource()
	{
		this(Transfer.NONE);
	}

	/**
	 * Create a source of drag operations.
	 * 
	 * @param operations
	 *            allowed operations
	 * 
	 * @see DND#MOVE
	 * @see DND#COPY
	 * @see DND#LINK
	 */
	public DragSource(int operations)
	{
		this.operations = operations;
	}

	/**
	 * Get possible transfers.
	 * 
	 * @return transfers
	 */
	public String[] getTransferTypes()
	{
		return new String[] { Transfer.ANY };
	}

	public DragSource drag(String selector)
	{
		this.selector = selector;

		if (this.initiateSelector.equals(Transfer.UNDEFINED))
		{
			this.initiateSelector = selector;
		}

		return this;
	}

	public DragSource initiate(String selector)
	{
		this.initiateSelector = selector;
		return this;
	}

	@Override
	public final void bind(Component component)
	{
		this.component = component;
		component.setOutputMarkupId(true);
	}

	@Override
	public final void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		response.renderJavascriptReference(PrototypeResourceReference.INSTANCE);

		renderDragHead(response);
	}

	private void renderDragHead(IHeaderResponse response)
	{
		response.renderJavascriptReference(Transfer.JS);

		final String id = component.getMarkupId();
		final String path = component.getPageRelativePath();

		String initJS = String.format("new DND.DragSource('%s','%s',%d,%s,'%s','%s');", id, path,
				getOperations(), new StringArrayFormattable(getTransferTypes()), selector,
				initiateSelector);
		response.renderOnDomReadyJavascript(initJS);
	}

	public int getOperations()
	{
		return operations;
	}

	final void beforeDrop(Request request, Transfer transfer)
	{
		Component drag = getDrag(request);

		beforeDrop(drag, transfer);
	}

	private Component getDrag(Request request)
	{
		String id = request.getParameter("drag");

		return MarkupIdVisitor.getComponent((MarkupContainer)component, id);
	}

	/**
	 * Notification that a drop is about to happen - any implementation shoudl
	 * set the data on the given transfer or reject it.
	 * 
	 * The default implementation uses the component's model object as transfer
	 * data.
	 * 
	 * @param drag
	 *            component to get data from
	 * @param operation
	 *            the drag's operation
	 * @param transfer
	 *            the transfer
	 * @see Transfer#setData(Object)
	 * @see Transfer#reject()
	 */
	public void beforeDrop(Component drag, Transfer transfer)
	{
		transfer.setData(drag.getDefaultModelObject());
	}

	/**
	 * Notification that a drop happened of one of this source's transfer datas.
	 * 
	 * The default implementation does nothing.
	 * 
	 * @param target
	 *            initiating request target
	 * @param transfer
	 *            the transfer
	 */
	public void afterDrop(AjaxRequestTarget target, Transfer transfer)
	{
	}

	/**
	 * Get the drag source of the given request.
	 * 
	 * @param request
	 *            request on which a drag happened
	 * @return drag source
	 */
	final static DragSource get(Request request)
	{
		String path = request.getParameter("source");

		Component component = request.getPage().get(path);

		if (component != null)
		{
			for (IBehavior behavior : component.getBehaviors())
			{
				if (behavior instanceof DragSource)
				{
					return (DragSource)behavior;
				}
			}
		}

		throw new PageExpiredException("No drag source found " + path);
	}
}
