/*
 * [The "BSD license"]
 *  Copyright (c) 2013 Terence Parr
 *  Copyright (c) 2013 Sam Harwell
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions
 *  are met:
 *
 *  1. Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *  3. The name of the author may not be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 *  IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 *  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 *  IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 *  INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 *  NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 *  THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 *  THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.antlr.v4.runtime.misc;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import java.util.Set;

/**
 * A compile-time validator for correct usage of the {@link NotNull} and
 * {@link Nullable} annotations.
 *
 * <p>The validation process checks the following items.</p>
 *
 * @author Sam Harwell
 */
@SupportedAnnotationTypes({NullUsageProcessor.NotNullClassName, NullUsageProcessor.NullableClassName})
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class NullUsageProcessor extends AbstractProcessor {
	public static final String NotNullClassName = "org.antlr.v4.runtime.misc.NotNull";
	public static final String NullableClassName = "org.antlr.v4.runtime.misc.Nullable";

	public NullUsageProcessor() {
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		if (!checkClassNameConstants()) {
			return true;
		}

		return true;
	}

	private boolean checkClassNameConstants() {
		boolean success = checkClassNameConstant(NotNullClassName, NotNull.class);
		success &= checkClassNameConstant(NullableClassName, Nullable.class);
		return success;
	}

	private boolean checkClassNameConstant(String className, Class<?> clazz) {
		if (className == null) {
			throw new NullPointerException("className");
		}

		if (clazz == null) {
			throw new NullPointerException("clazz");
		}

		if (!className.equals(clazz.getCanonicalName())) {
			processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, String.format("Unable to process null usage annotations due to class name mismatch: %s != %s", className, clazz.getCanonicalName()));
			return false;
		}

		return true;
	}
}
