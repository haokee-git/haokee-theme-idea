package org.haokee.theme;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtNameReferenceExpression;
import org.jetbrains.kotlin.psi.KtProperty;

public final class KotlinConstAnnotator implements Annotator {
  @Override
  public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
    if (holder.isBatchMode()) {
      return;
    }
    if (element instanceof KtProperty property) {
      if (!property.hasModifier(KtTokens.CONST_KEYWORD)) {
        return;
      }
      PsiElement name = property.getNameIdentifier();
      if (name != null) {
        highlight(name, holder);
      }
      return;
    }
    if (!(element instanceof KtNameReferenceExpression)) {
      return;
    }
    for (PsiReference reference : element.getReferences()) {
      PsiElement target = reference.resolve();
      if (target instanceof KtProperty property && property.hasModifier(KtTokens.CONST_KEYWORD)) {
        highlight(element, holder);
        return;
      }
    }
  }

  private static void highlight(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
    holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
      .range(element)
      .textAttributes(DefaultLanguageHighlighterColors.CONSTANT)
      .create();
  }
}
