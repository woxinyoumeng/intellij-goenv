package com.github.heyvito.goenv.services

import com.goide.vgo.configuration.VgoProjectSettings
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

class ProjectConfigurationService {
    companion object {
        private fun configureEnvironment(project: Project, privates: String,insecures: String) {
            ApplicationManager.getApplication().invokeLater {
                ApplicationManager.getApplication().runWriteAction {
                    val vgo = VgoProjectSettings.getInstance(project)
                    val env = vgo.environment
                    if (privates.isEmpty()) {
                        env.remove("GOPRIVATE")
                    } else {
                        env["GOPRIVATE"] = privates
                    }
                    if (insecures.isEmpty()){
                        env.remove("GOINSECURE")
                    }else{
                        env["GOINSECURE"] = insecures
                    }
                    vgo.setEnvironment(env)
                }
            }
        }
        fun configure(project: Project, using: VirtualFile) {
            val envFile = EnvParser(using.contentsToByteArray())
            this.configureEnvironment(project, envFile.privates.joinToString(","),envFile.insecures.joinToString(","))
        }

        fun reset(project: Project) {
            this.configureEnvironment(project, "","")
        }
    }
}
