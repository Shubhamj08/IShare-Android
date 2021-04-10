package com.shubham.ishare.ideas

data class Idea(
    var _id: String,
    var title: String,
    var description: String,
    var likes: List<String>,
    var liked: Boolean
)

 val iList = listOf(
     Idea("6060ab1ec7664334ac2b7222",
        "Idea 1",
        "This is demo idea 1. This is being used for frond end development.",
        listOf("6060ab1ec7664334ac2b7222",
            "60607c239a0b5013b80697ba",
            "6062fb38f65ac60c38ae6aa1"),
     true),

     Idea("6063ba452a37583c709388a2",
         "Idea 2",
         "This is demo idea 2. This is being used for frond end development.",
         listOf("6060ab1ec7664334ac2b7222",
             "60607c239a0b5013b80697ba",
             "6062fb38f65ac60c38ae6aa1"),
         false),

     Idea("60607c239a0b5013b80697ba",
         "Idea 3",
         "This is demo idea 3. This is being used for frond end development.",
         listOf("6060ab1ec7664334ac2b7222",
             "60607c239a0b5013b80697ba",
             "6062fb38f65ac60c38ae6aa1"),
         true),

     Idea("6061ba452a37583c709388a2",
         "Idea 4",
         "This is demo idea 4. This is being used for frond end development.",
         listOf("6060ab1ec7664334ac2b7222",
             "60607c239a0b5013b80697ba",
             "6062fb38f65ac60c38ae6aa1"),
         false),

     Idea("6062fb38f65ac60c38ae6aa1",
         "Idea 5",
         "This is demo idea 5. This is being used for frond end development.",
         listOf("6060ab1ec7664334ac2b7222",
             "60607c239a0b5013b80697ba",
             "6062fb38f65ac60c38ae6aa1"),
         true),
 )