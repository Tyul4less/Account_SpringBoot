package net.plang.HoWooAccount.system.common.filter;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.sitemesh.content.tagrules.html.DivExtractingTagRuleBundle;

public class SiteMeshFilter extends ConfigurableSiteMeshFilter {

   @Override
   protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {

      // Map default decorator. This shall be applied to all paths if no other paths match.
      builder.addDecoratorPath("/*", "/WEB-INF/views/decorators/decorator.jsp");
//           .addDecoratorPaths("/articles/*", "/decorators/article.html",
//                                             "/decoratos/two-page-layout.html",
//                                             "/decorators/common.html")
            // Exclude path from decoration.
        //    .addExcludedPath("/loginForm.html");
      builder.addTagRuleBundles(new DivExtractingTagRuleBundle());

   }

}