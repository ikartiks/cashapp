package com.kartik.grevocab.vm

import com.google.gson.reflect.TypeToken
import com.kartik.grevocab.BaseTest
import com.kartik.grevocab.FileReader
import com.kartik.grevocab.MockResProvider
import com.kartik.grevocab.R
import com.kartik.grevocab.adapters.display.GroupTableDisplay
import com.kartik.grevocab.api.pojos.Group
import com.kartik.grevocab.api.pojos.Word
import com.kartik.grevocab.utility.DBUtility
import com.kartik.grevocab.utility.toRoomObj
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

// Note if you want something like telstra where you want a real response, you will need to have a android test
// (not java as we wont be able to access debug/assets folder)
// and then read responses from a file like we do in api
@ExperimentalCoroutinesApi
class FragmentWordPacketViewModelTest : BaseTest() {

    private val dbUtility: DBUtility = mockk()
    private val mockResProvider: MockResProvider = mockk()
    private lateinit var vm: FragmentWordPacketViewModel

    @BeforeEach
    fun setUp() {
        vm = FragmentWordPacketViewModel(dbUtility, mockResProvider)

        val groups = parseGroups()
        val firstGroup = groups.first { it.id == 2 }
        firstGroup.totalWords = 47 // this data doesn't come from api, we do it while fetching from db

        val words = getWordListForGroup()
        val wordsTableRoomList = ArrayList(words.map { it.toRoomObj() })
        vm.stockDisplay = GroupTableDisplay(firstGroup.toRoomObj(), wordsTableRoomList, "Group 1", R.color.colorAccent)

        every { mockResProvider.getString(R.string.word_text, any(), any()) } returns "hahaha"
        every { mockResProvider.getString(R.string.similar_meaning_packet, any()) } returns "hahaha"
        val worldsListForGroup = getWordListForGroup()

        vm.initialiseKnownAndUnknownWords()
    }

    private fun parseGroups(): ArrayList<Group> {
        // since this is a java test cannot fetch resources from debug folder
        return FileReader.getData("groups.json", object : TypeToken<ArrayList<Group>>() {}.type)
    }

    private fun getWordListForGroup(): ArrayList<Word> {

        val allWords = FileReader.getData<ArrayList<Word>>("words.json", object : TypeToken<ArrayList<Word>>() {}.type)
        val group2Words = allWords.filter { it.groupId == 2 }
        val arrayList = ArrayList<Word>()
        arrayList.addAll(group2Words)
        return arrayList
    }

    @Test
    fun testFirstWordAndGroup() {
        vm.bringNextWord()
        var currentWord = vm.currentWordObservable.get()?.word
        var masteredWords = vm.masteredWords.get()

        Assert.assertEquals(currentWord?.name, "Amalgam")
        Assert.assertEquals(masteredWords, 0)

        coEvery { dbUtility.updateWordsTable(any()) } returns Unit

        vm.iKnowThisWordClicked()
        vm.iKnowThisWordClicked()
        vm.iKnowThisWordClicked()

        currentWord = vm.currentWordObservable.get()?.word
        masteredWords = vm.masteredWords.get()

        Assert.assertEquals(currentWord?.name, "Admonish")
        Assert.assertEquals(masteredWords, 3)
    }

    @Test
    fun testIDoNotKnowThisWordClicked() {
        vm.bringNextWord()

        var currentWord = vm.currentWordObservable.get()?.word
        var masteredWords = vm.masteredWords.get()

        Assert.assertEquals(currentWord?.name, "Amalgam")
        Assert.assertEquals(masteredWords, 0)

        coEvery { dbUtility.updateWordsTable(any()) } returns Unit

        vm.proceedClicked("hahah")

        vm.iKnowThisWordClicked()
        vm.iKnowThisWordClicked()
        vm.iKnowThisWordClicked()
        vm.iKnowThisWordClicked()
        vm.iKnowThisWordClicked()

        currentWord = vm.currentWordObservable.get()?.word
        masteredWords = vm.masteredWords.get()

        Assert.assertEquals(currentWord?.name, "Amalgam")
        Assert.assertEquals(masteredWords, 5)
        Assert.assertEquals(currentWord?.comment, "hahah")
        Assert.assertEquals(currentWord?.understoodCount, 5)
    }

    @Test
    fun testIDoNotKnowThisWordClickedAndKnowThisWordClickedOnSameWord() {
        vm.bringNextWord()

        var currentWord = vm.currentWordObservable.get()?.word
        var masteredWords = vm.masteredWords.get()

        Assert.assertEquals(currentWord?.name, "Amalgam")
        Assert.assertEquals(masteredWords, 0)

        coEvery { dbUtility.updateWordsTable(any()) } returns Unit

        vm.proceedClicked("hahah")

        vm.iKnowThisWordClicked()
        vm.iKnowThisWordClicked()
        vm.iKnowThisWordClicked()
        vm.iKnowThisWordClicked()
        vm.iKnowThisWordClicked()

        currentWord = vm.currentWordObservable.get()?.word
        masteredWords = vm.masteredWords.get()

        Assert.assertEquals(currentWord?.name, "Amalgam")
        Assert.assertEquals(masteredWords, 5)
        Assert.assertEquals(currentWord?.comment, "hahah")
        Assert.assertEquals(currentWord?.understoodCount, 5)

        // for amalgam
        vm.iKnowThisWordClicked()

        // to bring amalgam back
        vm.iKnowThisWordClicked()
        vm.iKnowThisWordClicked()
        vm.iKnowThisWordClicked()
        vm.iKnowThisWordClicked()
        vm.iKnowThisWordClicked()

        currentWord = vm.currentWordObservable.get()?.word
        masteredWords = vm.masteredWords.get()

        Assert.assertEquals(currentWord?.name, "Amalgam")
        Assert.assertEquals(masteredWords, 10)
        Assert.assertEquals(currentWord?.comment, "hahah")
        Assert.assertEquals(currentWord?.understoodCount, 4)
    }
}
