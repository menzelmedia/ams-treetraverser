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

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;


/**
 * <pre>
 * $Author: Mathias Menzel-Nielsen <Mathias.Menzel-Nielsen@arvato-mobile.com> $
 * $Date: 28.09.2009 $
 * $Revision$
 * </pre>
 *
 * <p>Implementation of a JAXB2 Tree Traverser, which finds (sub)collections via reflection and calls handlers</p>
 *
 * <p>In facto not very JAXB centric -- can be used for anything which has tree structures with collections</p>
 *
 * <p>GeiloCode(TM)</p>
 *
 * Copyright (C) 2009 arvato mobile GmbH. All rights reserved.
 * @author MENZE07
 */
public class ReflectionTreeTraverser {
    Logger logger = Logger.getLogger(this.getClass());

    private TreeIteratorHandler handler;

    private List<Package> restrictToPackages = new LinkedList<Package>();

    private static boolean isInterfacing(Class clazz, Class theinterface){
        if(clazz == null || clazz.getInterfaces() == null) return false;
        for(Class i : clazz.getInterfaces()){
            if(i.equals(theinterface)) return true;
        }
        return false;
    }

    public void treeTraverse(Object nodeObject) throws RuntimeException{
        try
        {
            treeTraverseImpl(nodeObject);
        }
        catch( Exception error )
        {
            throw new RuntimeException("ReflectionTreeTraverser.treeTraverseImpl failed.", error );
        }
    }
    
    private void treeTraverseImpl(Object nodeObject) throws Exception{
        if(nodeObject == null) return;
        if(getRestrictToPackages()!=null && !getRestrictToPackages().isEmpty() && !getRestrictToPackages().contains(nodeObject.getClass().getPackage())) {
            logger.debug("Package "+nodeObject.getClass().getPackage().getName()+" is not A package of interest -- skipping node");
            return;
        }
        logger.debug("invoking handler for node "+nodeObject.getClass().getName());
        Method enterMethod = null;
        try{
            enterMethod =  getHandler().getClass().getMethod("enter", nodeObject.getClass());
        }catch(NoSuchMethodException nex){
        }
        Method leaveMethod = null;
        try{
            leaveMethod = getHandler().getClass().getMethod("leave", nodeObject.getClass());
        }catch(NoSuchMethodException nex){
        }
        if (enterMethod == null) {
            logger.debug("No enter handler found for node");
        } else {
                enterMethod.invoke(getHandler(), nodeObject);
        }
        for(Method method : nodeObject.getClass().getMethods()){
            if(method.getName().startsWith("get") && !method.getName().equals("getClass")){
                logger.debug("found getter: "+method.getName());
                if(method.getReturnType()!=null) logger.debug("returns: "+method.getReturnType().getName());
                if (method.getReturnType() != null) {
                    if (isInterfacing(method.getReturnType(), Collection.class)) {
                        logger.debug("is collection! iterating");
                        Collection col = (Collection) method.invoke(nodeObject);
                        Iterator iter = col.iterator();
                        while (iter.hasNext()) {
                            Object node = iter.next();
                            logger.debug("Handling node: " + node.getClass().getName());
                            logger.debug("going deeper");
                            //and go deeper
                            treeTraverse(node);
                        }
                    }else{
                        if(restrictToPackages!=null && !restrictToPackages.isEmpty() && !restrictToPackages.contains(method.getReturnType().getPackage()))
                            continue;

                        logger.debug("is no collection just traversing");
                        if(method.getParameterTypes()!=null && method.getParameterTypes().length==0)
                            treeTraverse(method.invoke(nodeObject));
                    }
                }
                
            }
        }
        if (leaveMethod == null) {
            logger.debug("No leave handler found for node");
        } else {
            leaveMethod.invoke(getHandler(), nodeObject);
        }
    }

    /**
     * @return the handler
     */
    public TreeIteratorHandler getHandler() {
        return handler;
    }

    /**
     * @param handler the handler to set
     */
    public void setHandler(TreeIteratorHandler handler) {
        this.handler = handler;
    }


    public List<Package> getRestrictToPackages(){
        return restrictToPackages;
    }
    
    public void setRestrictToPackages(List<Package> packages){
        setRestrictToPackages(packages);
    }

    /**
     * @param restrictToPackage the restrictToPackage to set
     */
    public void setRestrictToPackage(Package restrictToPackage) {
        getRestrictToPackages().add(restrictToPackage);
    }

}
