package cy.ac.ucy.cs.anyplace.lib.models


import com.google.gson.annotations.SerializedName

data class Spaces(
    @SerializedName("spaces")
    val spaces: List<Space>
)