package com.ksyun.vm.pojo.snapshot;

/**
 * User: liuchuandong
 * Date: 13-10-12
 * Time: 下午6:30
 * Func:
 */
class VMMetadata {
    private String image_location;
    private String image_state;
    private String user_id;
    private String os_version;
    private String kernel_id;
    private String ramdisk_id;
    private String image_type;
    private String storage_locate;
    private String vm_id;
    private String base_image_ref;
    private String owner_id;

    String getImage_location() {
        return image_location;
    }

    void setImage_location(String image_location) {
        this.image_location = image_location;
    }

    String getImage_state() {
        return image_state;
    }

    void setImage_state(String image_state) {
        this.image_state = image_state;
    }

    String getUser_id() {
        return user_id;
    }

    void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    String getOs_version() {
        return os_version;
    }

    void setOs_version(String os_version) {
        this.os_version = os_version;
    }

    String getKernel_id() {
        return kernel_id;
    }

    void setKernel_id(String kernel_id) {
        this.kernel_id = kernel_id;
    }

    String getRamdisk_id() {
        return ramdisk_id;
    }

    void setRamdisk_id(String ramdisk_id) {
        this.ramdisk_id = ramdisk_id;
    }

    String getImage_type() {
        return image_type;
    }

    void setImage_type(String image_type) {
        this.image_type = image_type;
    }

    String getStorage_locate() {
        return storage_locate;
    }

    void setStorage_locate(String storage_locate) {
        this.storage_locate = storage_locate;
    }

    String getVm_id() {
        return vm_id;
    }

    void setVm_id(String vm_id) {
        this.vm_id = vm_id;
    }

    String getBase_image_ref() {
        return base_image_ref;
    }

    void setBase_image_ref(String base_image_ref) {
        this.base_image_ref = base_image_ref;
    }

    String getOwner_id() {
        return owner_id;
    }

    void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }
}
