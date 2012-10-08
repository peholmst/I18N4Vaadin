/*
 * Copyright (c) 2012 Petter Holmström
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.peholmst.i18n4vaadin.ap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.lang.model.element.PackageElement;

/**
 * Class containing a collection of {@link PackageInfo} objects. <p><b>This
 * class is internal and should never be used by clients.</b>
 *
 * @author Petter Holmström
 */
final class PackageMap {

    private final Map<String, PackageInfo> packages = new HashMap<String, PackageInfo>();

    PackageInfo getPackage(PackageElement pkg) {
        PackageInfo pkgInfo = packages.get(pkg.getQualifiedName().toString());
        if (pkgInfo == null) {
            return addPackage(pkg);
        } else {
            return pkgInfo;
        }
    }

    PackageInfo addPackage(PackageElement pkg) {
        PackageInfo pkgInfo = new PackageInfo(this, pkg);
        packages.put(pkg.getQualifiedName().toString(), pkgInfo);
        return pkgInfo;
    }

    PackageInfo getPackageByName(String packageName) {
        return packages.get(packageName);
    }

    Set<PackageInfo> getRootPackages() {
        final Set<PackageInfo> roots = new HashSet<PackageInfo>();
        for (PackageInfo pkgInfo : packages.values()) {
            if (pkgInfo.isRoot()) {
                roots.add(pkgInfo);
            }
        }
        return roots;
    }

    Set<PackageInfo> getSubPackages(PackageInfo parent) {
        final Set<PackageInfo> subPackages = new HashSet<PackageInfo>();
        for (PackageInfo pkgInfo : packages.values()) {
            if (pkgInfo.getName().startsWith(parent.getName())) {
                subPackages.add(pkgInfo);
            }
        }
        return subPackages;
    }
    
    Set<PackageInfo> getAllPackages() {
        return new HashSet<PackageInfo>(packages.values());
    }
}
