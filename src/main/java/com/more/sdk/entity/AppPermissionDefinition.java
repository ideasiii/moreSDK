package com.more.sdk.entity;

import java.util.Date;

public class AppPermissionDefinition {
    private Integer id;
    private Integer permissionType;
    private String description;
    private Date createDate;
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app_permission_definition.id
     *
     * @param id the value for app_permission_definition.id
     *
     * @mbggenerated Tue Jan 16 17:37:14 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app_permission_definition.permission_type
     *
     * @return the value of app_permission_definition.permission_type
     *
     * @mbggenerated Tue Jan 16 17:37:14 CST 2018
     */
    public Integer getPermissionType() {
        return permissionType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app_permission_definition.permission_type
     *
     * @param permissionType the value for app_permission_definition.permission_type
     *
     * @mbggenerated Tue Jan 16 17:37:14 CST 2018
     */
    public void setPermissionType(Integer permissionType) {
        this.permissionType = permissionType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app_permission_definition.description
     *
     * @return the value of app_permission_definition.description
     *
     * @mbggenerated Tue Jan 16 17:37:14 CST 2018
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app_permission_definition.description
     *
     * @param description the value for app_permission_definition.description
     *
     * @mbggenerated Tue Jan 16 17:37:14 CST 2018
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app_permission_definition.create_date
     *
     * @return the value of app_permission_definition.create_date
     *
     * @mbggenerated Tue Jan 16 17:37:14 CST 2018
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app_permission_definition.create_date
     *
     * @param createDate the value for app_permission_definition.create_date
     *
     * @mbggenerated Tue Jan 16 17:37:14 CST 2018
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}