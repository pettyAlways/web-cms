package org.yingzuidou.cms.cmsweb.dto;
/**
 * OrganizationDTO description goes here
 *
 * @author dell
 * @date 2018/9/15
 */

import org.yingzuidou.cms.cmsweb.entity.OrganizationEntity;

import java.util.List;

/**
 * 组织类DTO
 *
 * @author yingzuidou
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

    private String orgName;

    private Integer parentId;

    private String parentName;

    private String expand;

    /**
     * 孩子节点
     */
    List<OrganizationDTO> children;

    List<OrganizationEntity> childrenEntityList;

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

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getExpand() {
        return expand;
    }

    public void setExpand(String expand) {
        this.expand = expand;
    }

    public List<OrganizationEntity> getChildrenEntityList() {
        return childrenEntityList;
    }

    public void setChildrenEntityList(List<OrganizationEntity> childrenEntityList) {
        this.childrenEntityList = childrenEntityList;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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
