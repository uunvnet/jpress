/**
 * Copyright (c) 2015-2016, Michael Yang 杨福海 (fuhai999@gmail.com).
 *
 * Licensed under the GNU Lesser General Public License (LGPL) ,Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.jpress.core.render;

import com.jfinal.render.IErrorRenderFactory;
import com.jfinal.render.Render;
import com.jfinal.render.TextRender;

import io.jpress.core.Jpress;
import io.jpress.template.Template;
import io.jpress.template.TemplateUtils;

public class JErrorRenderFactory implements IErrorRenderFactory {

	@Override
	public Render getRender(int errorCode, String view) {
		if (!Jpress.isInstalled()) {
			return new TextRender(errorCode + " error in jpress.");
		}

		Template template = TemplateUtils.currentTemplate();
		if (null == template) {
			return new TextRender(String.format("%s error! you haven't configure your template yet.", errorCode));
		}

		String errorHtml = TemplateUtils.currentTemplate().getPath() + "/" + errorCode + ".html";

		String renderType = TemplateUtils.currentTemplate().getRenderType();
		
		//the default render type is freemarker
		if (renderType == null || "".equals(renderType.trim())) {
			return new JFreemarkerRender(errorHtml);
		}

		if ("freemarker".equalsIgnoreCase(renderType)) {
			return new JFreemarkerRender(errorHtml);
		} else if ("thymeleaf".equalsIgnoreCase(renderType)) {
			return new ThymeleafRender(errorHtml);
		}

		return new TextRender(errorCode + " error in jpress.");
	}

}
