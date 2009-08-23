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
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.wicketstuff.prototype.PrototypeResourceReference;

import wicketdnd.util.MarkupIdVisitor;

/**
 * @see #getTransferData(Component)
 * @see #onDropped(AjaxRequestTarget, Object, int)
 * 
 * @author Sven Meier
 */
public abstract class DragSource extends AbstractBehavior {

	private static final long serialVersionUID = 1L;

	private Component component;

	private String selector;

	private int operations;

	/**
	 * Create a source of drag operations.
	 * 
	 * @param operations
	 *            allowed operations
	 * @param selector
	 *            CSS selector
	 * 
	 * @see DND#MOVE
	 * @see DND#COPY
	 * @see DND#LINK
	 */
	public DragSource(int operations, String selector) {
		this.operations = operations;
		this.selector = selector;
	}

	@Override
	public final void bind(Component component) {
		this.component = component;
		component.setOutputMarkupId(true);
	}

	@Override
	public final void renderHead(IHeaderResponse response) {
		super.renderHead(response);

		response.renderJavascriptReference(PrototypeResourceReference.INSTANCE);

		renderDragHead(response);
	}

	private void renderDragHead(IHeaderResponse response) {
		response.renderJavascriptReference(DND.JS);

		final String id = component.getMarkupId();
		String initJS = String.format("new DragSource('%s',%d,'%s');", id,
				operations, selector);
		response.renderOnDomReadyJavascript(initJS);
	}

	final Object getTransferData(int operation) {
		Component drag = findDrag();

		return getTransferData(drag, operation);
	}

	private Component findDrag() {
		String id = component.getRequest().getParameter("drag");

		return MarkupIdVisitor.getComponent((MarkupContainer) component, id);
	}

	/**
	 * Get the data to transfer from the given dragged component - the default
	 * implementation returns the component's model object.
	 * 
	 * @param drag
	 *            component to get data from
	 * @param operation
	 * @return transfer data
	 */
	public Object getTransferData(Component drag, int operation) {
		return drag.getDefaultModelObject();
	}

	/**
	 * Notification that a drop happend of one of this source's transfer datas
	 * 
	 * The default implementation does nothing.
	 * 
	 * @param target
	 *            initiating request target
	 * @param transferData
	 *            the transferred data
	 * @param operation
	 *            the DND operation
	 */
	public void onDropped(AjaxRequestTarget target, Object transferData,
			int operation) {
	}
}
