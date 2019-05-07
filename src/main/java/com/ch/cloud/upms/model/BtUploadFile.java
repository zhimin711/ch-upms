package com.ch.cloud.upms.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "bt_upload_file")
public class BtUploadFile implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 原文件名
     */
    @Column(name = "ORIGINAL_NAME")
    private String originalName;

    /**
     * 文件名
     */
    @Column(name = "FILE_NAME")
    private String fileName;

    /**
     * 文件后缀
     */
    @Column(name = "FILE_TYPE")
    private String fileType;

    /**
     * 文件大小
     */
    @Column(name = "FILE_SIZE")
    private Long fileSize;

    /**
     * 文件存储相对路径(不包含共享路径)
     */
    @Column(name = "FILE_PATH")
    private String filePath;

    /**
     * 文件内容类型
     */
    @Column(name = "CONTENT_TYPE")
    private String contentType;

    /**
     * 文件MD5
     */
    @Column(name = "MD5")
    private String md5;

    /**
     * 上传类型(1.公共类型 2.用户类型 3.)
     */
    @Column(name = "TYPE")
    private String type;

    /**
     * 版本（自定义）
     */
    @Column(name = "VERSION")
    private String version;

    /**
     * 系统来源: 0.ADMIN 1.BOOKS
     */
    @Column(name = "SRC_TYPE")
    private String srcType;

    /**
     * 状态：0.待处理 1.可用 2.拒绝 3.删除 4.异常
     */
    @Column(name = "STATUS")
    private String status;

    /**
     * 描述
     */
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_AT")
    private Date createAt;

    /**
     * 创建人
     */
    @Column(name = "CREATE_BY")
    private String createBy;

    /**
     * 更新时间
     */
    @Column(name = "UPDATE_AT")
    private Date updateAt;

    /**
     * 更新人
     */
    @Column(name = "UPDATE_BY")
    private String updateBy;

    private static final long serialVersionUID = 1L;

    /**
     * 获取主键
     *
     * @return ID - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取原文件名
     *
     * @return ORIGINAL_NAME - 原文件名
     */
    public String getOriginalName() {
        return originalName;
    }

    /**
     * 设置原文件名
     *
     * @param originalName 原文件名
     */
    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    /**
     * 获取文件名
     *
     * @return FILE_NAME - 文件名
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 设置文件名
     *
     * @param fileName 文件名
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 获取文件后缀
     *
     * @return FILE_TYPE - 文件后缀
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * 设置文件后缀
     *
     * @param fileType 文件后缀
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * 获取文件大小
     *
     * @return FILE_SIZE - 文件大小
     */
    public Long getFileSize() {
        return fileSize;
    }

    /**
     * 设置文件大小
     *
     * @param fileSize 文件大小
     */
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * 获取文件存储相对路径(不包含共享路径)
     *
     * @return FILE_PATH - 文件存储相对路径(不包含共享路径)
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * 设置文件存储相对路径(不包含共享路径)
     *
     * @param filePath 文件存储相对路径(不包含共享路径)
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 获取文件内容类型
     *
     * @return CONTENT_TYPE - 文件内容类型
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * 设置文件内容类型
     *
     * @param contentType 文件内容类型
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * 获取文件MD5
     *
     * @return MD5 - 文件MD5
     */
    public String getMd5() {
        return md5;
    }

    /**
     * 设置文件MD5
     *
     * @param md5 文件MD5
     */
    public void setMd5(String md5) {
        this.md5 = md5;
    }

    /**
     * 获取上传类型(1.公共类型 2.用户类型 3.)
     *
     * @return TYPE - 上传类型(1.公共类型 2.用户类型 3.)
     */
    public String getType() {
        return type;
    }

    /**
     * 设置上传类型(1.公共类型 2.用户类型 3.)
     *
     * @param type 上传类型(1.公共类型 2.用户类型 3.)
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取版本（自定义）
     *
     * @return VERSION - 版本（自定义）
     */
    public String getVersion() {
        return version;
    }

    /**
     * 设置版本（自定义）
     *
     * @param version 版本（自定义）
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * 获取系统来源: 0.ADMIN 1.BOOKS
     *
     * @return SRC_TYPE - 系统来源: 0.ADMIN 1.BOOKS
     */
    public String getSrcType() {
        return srcType;
    }

    /**
     * 设置系统来源: 0.ADMIN 1.BOOKS
     *
     * @param srcType 系统来源: 0.ADMIN 1.BOOKS
     */
    public void setSrcType(String srcType) {
        this.srcType = srcType;
    }

    /**
     * 获取状态：0.待处理 1.可用 2.拒绝 3.删除 4.异常
     *
     * @return STATUS - 状态：0.待处理 1.可用 2.拒绝 3.删除 4.异常
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态：0.待处理 1.可用 2.拒绝 3.删除 4.异常
     *
     * @param status 状态：0.待处理 1.可用 2.拒绝 3.删除 4.异常
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取描述
     *
     * @return DESCRIPTION - 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取创建时间
     *
     * @return CREATE_AT - 创建时间
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * 设置创建时间
     *
     * @param createAt 创建时间
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * 获取创建人
     *
     * @return CREATE_BY - 创建人
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建人
     *
     * @param createBy 创建人
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取更新时间
     *
     * @return UPDATE_AT - 更新时间
     */
    public Date getUpdateAt() {
        return updateAt;
    }

    /**
     * 设置更新时间
     *
     * @param updateAt 更新时间
     */
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    /**
     * 获取更新人
     *
     * @return UPDATE_BY - 更新人
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * 设置更新人
     *
     * @param updateBy 更新人
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", originalName=").append(originalName);
        sb.append(", fileName=").append(fileName);
        sb.append(", fileType=").append(fileType);
        sb.append(", fileSize=").append(fileSize);
        sb.append(", filePath=").append(filePath);
        sb.append(", contentType=").append(contentType);
        sb.append(", md5=").append(md5);
        sb.append(", type=").append(type);
        sb.append(", version=").append(version);
        sb.append(", srcType=").append(srcType);
        sb.append(", status=").append(status);
        sb.append(", description=").append(description);
        sb.append(", createAt=").append(createAt);
        sb.append(", createBy=").append(createBy);
        sb.append(", updateAt=").append(updateAt);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}