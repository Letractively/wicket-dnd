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

import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;

/**
 * @author Sven Meier
 */
public class DND
{
	public static final ResourceReference JS = new JavascriptResourceReference(DND.class,
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
}
