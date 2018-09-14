package org.yingzuidou.cms.cmsweb.dto;
/**
 * OrganizationDTO description goes here
 *
 * @author dell
 * @date 2018/9/15
 */

import java.util.List;

/**
 * @author dell
 * @date 2018/9/15     
 */
public class OrganizationDTO{
    /**
     * 树节点ID
     */
    private Integer id;

    /**
     * 树节点名字
     */
    private String label;

    /**
     * 孩子节点
     */
    List<OrganizationDTO> children;

    public List<OrganizationDTO> getChildren() {
        return children;
    }

    public void setChildren(List<OrganizationDTO> children) {
        this.children = children;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "OrganizationDTO{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", children=" + children +
                '}';
    }
}
