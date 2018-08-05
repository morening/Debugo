package com.morening.debugo.debugo_plugin

class DebugoExtension {
  def enabled = true

  def setEnabled(boolean enabled) {
    this.enabled = enabled
  }

  def getEnabled() {
    return enabled;
  }
}
