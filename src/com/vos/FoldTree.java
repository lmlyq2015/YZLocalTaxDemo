package com.vos;

import java.util.ArrayList;
import java.util.List;

public class FoldTree {

	private int id;
	private String iconCls;
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	private String text;
	private String state;
	private int pid;
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	private List<FoldTree> children = new ArrayList<FoldTree>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public List<FoldTree> getChildren() {
		return children;
	}
	public void setChildren(List<FoldTree> children) {
		this.children = children;
	}  

	public static List<FoldTree> formatTree(List<FoldTree> list) {

		FoldTree root = new FoldTree();
		FoldTree node = new FoldTree();
		List<FoldTree> treelist = new ArrayList<FoldTree>();// 拼凑好的json格式的数据
		List<FoldTree> parentnodes = new ArrayList<FoldTree>();// parentnodes存放所有的父节点

		if (list != null && list.size() > 0) {
			root = list.get(0);
			// 循环遍历oracle树查询的所有节点
			for (int i = 1; i < list.size(); i++) {
				node = list.get(i);
				if (node.getPid() == (root.getId())) {
					// 为tree root 增加子节点
					parentnodes.add(node);
					root.getChildren().add(node);
				} else {// 获取root子节点的孩子节点
					getChildrenNodes(parentnodes, node);
					parentnodes.add(node);
				}
			}
		}
		treelist.add(root);
		return treelist;

	}
	private static void getChildrenNodes(List<FoldTree> parentnodes, FoldTree node) {
		//循环遍历所有父节点和node进行匹配，确定父子关系
		for (int i = parentnodes.size() - 1; i >= 0; i--) {

			FoldTree pnode = parentnodes.get(i);
			// 如果是父子关系，为父节点增加子节点，退出for循环
			if (pnode.getId() == (node.getPid())) {
				pnode.setState("closed");// 关闭二级树
				pnode.getChildren().add(node);
				return;
			} else {
				// 如果不是父子关系，删除父节点栈里当前的节点，
				// 继续此次循环，直到确定父子关系或不存在退出for循环
				parentnodes.remove(i);
			}
		}
	}
	public static List<FoldTree> buildtree(List<FoldTree> nodes,int id){
		List<FoldTree> FoldTrees=new ArrayList<FoldTree>();
		for (FoldTree FoldTree : nodes) {
			FoldTree node=new FoldTree();
			node.setId(FoldTree.getId());
			node.setText(FoldTree.getText());
			if(id == FoldTree.getPid()){
				node.setChildren(buildtree(nodes, node.getId()));
				FoldTrees.add(node);
			}
			
		}
		return FoldTrees;
	}
}
