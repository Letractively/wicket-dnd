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
	 * Action indicating a move.
	 */
	public static final int MOVE = 1;

	/**
	 * Action indicating a copy.
	 */
	public static final int COPY = 2;
	
	/**
	 * Action indicating a link.
	 */
	public static final int LINK = 4;
}
