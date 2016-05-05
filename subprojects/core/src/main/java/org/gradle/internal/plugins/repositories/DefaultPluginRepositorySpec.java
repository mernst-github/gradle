/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.internal.plugins.repositories;

import org.gradle.api.Action;
import org.gradle.api.internal.file.FileResolver;
import org.gradle.api.plugins.dsl.PluginRepositorySpec;
import org.gradle.api.plugins.repositories.GradlePluginPortal;
import org.gradle.api.plugins.repositories.IvyPluginRepository;
import org.gradle.api.plugins.repositories.MavenPluginRepository;

/**
 * Bridges between a global PluginRepositorySpec and a {@link org.gradle.api.Script}.
 */
public class DefaultPluginRepositorySpec implements PluginRepositorySpec {
    private final PluginRepositoryFactory pluginRepositoryFactory;
    private final PluginRepositoryRegistry pluginRepositoryRegistry;
    private final FileResolver fileResolver;

    public DefaultPluginRepositorySpec(PluginRepositoryFactory pluginRepositoryFactory, PluginRepositoryRegistry pluginRepositoryRegistry, FileResolver fileResolver) {
        this.pluginRepositoryFactory = pluginRepositoryFactory;
        this.pluginRepositoryRegistry = pluginRepositoryRegistry;
        this.fileResolver = fileResolver;
    }

    @Override
    public MavenPluginRepository maven(Action<? super MavenPluginRepository> action) {
        MavenPluginRepository repo = pluginRepositoryFactory.maven(action, fileResolver);
        pluginRepositoryRegistry.add(repo);
        return repo;
    }

    @Override
    public IvyPluginRepository ivy(Action<? super IvyPluginRepository> action) {
        IvyPluginRepository repo = pluginRepositoryFactory.ivy(action, fileResolver);
        pluginRepositoryRegistry.add(repo);
        return repo;
    }

    @Override
    public GradlePluginPortal gradlePluginPortal() {
        GradlePluginPortal portal = pluginRepositoryFactory.gradlePluginPortal();
        pluginRepositoryRegistry.add(portal);
        return portal;
    }
}
