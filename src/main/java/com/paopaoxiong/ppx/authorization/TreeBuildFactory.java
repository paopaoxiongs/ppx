package com.paopaoxiong.ppx.authorization;

import com.paopaoxiong.ppx.common.TreeNode;
import com.paopaoxiong.ppx.model.system.Menu;

import java.util.ArrayList;
import java.util.List;

public class TreeBuildFactory {

    private List<TreeNode> treeNodes;

    public List<TreeNode> getTreeNodes(){
        return treeNodes;
    }

    public TreeBuildFactory(List<Menu> menuList){
        if(null == menuList || menuList.size() == 0){
            throw new IllegalArgumentException("输入参数错误");
        }
        List<TreeNode> list = new ArrayList<>();
        for (Menu menu: menuList){
            TreeNode treeNode = new TreeNode();
            treeNode.setId(menu.getMenuId());
            treeNode.setName(menu.getName());
            treeNode.setParentId(menu.getParentId());
            treeNode.setUrl(menu.getUrl());
            treeNode.setIcon(menu.getIcon());
            treeNode.setType(menu.getType());
            treeNode.setPermission(menu.getPermission());
            treeNode.setOrderNum(menu.getOrderNum());

            list.add(treeNode);
        }
        treeNodes = list;
    }

    public TreeNode buildTreeWithRoot() {
        TreeNode treeNode = findRoot();
        if (null == treeNode) {
            throw new IllegalArgumentException("未找到根节点,请调用buildTreeWithRoot(Integer id)方法");
        }
        Integer id = treeNode.getId();
        return buildTreeWithRoot(id);
    }

    /**
     * 构建树形结构,返回节点树
     * @param id
     * @return
     */
    public TreeNode buildTreeWithRoot(Integer id) {
        if (null == id) {
            throw new IllegalArgumentException("id参数输入错误");
        }
        return recursion(id);
    }

    /**
     * 递归建立树形关系
     * @param id
     * @return
     */
    private TreeNode recursion(Integer id){
        TreeNode node = findParent(id);
        List<TreeNode> childTreeNodes = findChild(id);
        for (TreeNode tn: childTreeNodes){
            TreeNode t = recursion(tn.getId());
            node.getChildNodes().add(t);
        }
        return node;
    }

    /**
     * 查找父TreeNode
     * @param id
     * @return
     */
    private TreeNode findParent(Integer id){
        for (TreeNode treeNode: getTreeNodes()){
            if(id.equals(treeNode.getId())){
                return treeNode;
            }
        }
        return null;
    }

    /**
     * 查找子节点
     * @param id
     * @return
     */
    private List<TreeNode> findChild(Integer id) {
        List<TreeNode> childTreeNodes = new ArrayList<>();
        for (TreeNode treeNode: getTreeNodes()){
            if (id.equals(treeNode.getParentId())){
                childTreeNodes.add(treeNode);
            }
        }
        return childTreeNodes;
    }

    /**
     * 获取list中parentId为null的节点,以此节点为根节点
     */
    public TreeNode findRoot() {
        for (TreeNode treeNode : getTreeNodes()) {
            if (0 == treeNode.getParentId()) {
                return treeNode;
            }
        }
        return null;
    }


}
