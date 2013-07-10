/*
 * Copyright 2009 arvato mobile GmbH. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of arvato mobile GmbH ("Confidential Information").
 * You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement
 * you entered into with arvato mobile GmbH.
 */

package com.mondiamedia.treetraverser;

/**
 * Tagging interface to tag IteratorHandlers used by <code>JAXBReflectionTreeTraverser</code>.
 * <p>Will be called by reflection. Create one handle(XXXXObject node) Method per Objectclass in the jaxb2 tree you want to handle</p>
 * @author MENZE07
 */
public interface TreeIteratorHandler {
    

}
