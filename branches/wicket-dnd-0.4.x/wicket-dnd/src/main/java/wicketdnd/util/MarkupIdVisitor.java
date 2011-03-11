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
package wicketdnd.util;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Component.IVisitor;
import org.apache.wicket.protocol.http.PageExpiredException;

/**
 * Find a child component by it's markup id.
 * 
 * @author Sven Meier
 */
public class MarkupIdVisitor implements IVisitor<Component> {

	private final String id;

	public MarkupIdVisitor(String id) {
		if (id == null) {
			throw new IllegalArgumentException("id must not be null");
		}
		this.id = id;
	}

	public Object component(Component component) {
		if (id.equals(component.getMarkupId(false))) {
			return component;
		}
		return IVisitor.CONTINUE_TRAVERSAL;
	}

	/**
	 * Get the given container's descendent by markup id.
	 * 
	 * @param container
	 *            container to find descendent of
	 * @param id
	 *            markup id
	 * @return component
	 * @throws PageExpiredException
	 *             if no descendent has the given markup id
	 */
	public static Component getComponent(MarkupContainer container, String id) {
		Component component = (Component) container
				.visitChildren(new MarkupIdVisitor(id));

		if (component == null) {
			throw new PageExpiredException("No component with markup id " + id);
		}

		return component;
	}
}