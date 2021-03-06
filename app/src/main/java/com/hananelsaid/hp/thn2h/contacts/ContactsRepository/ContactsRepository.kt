package com.hananelsaid.hp.thn2h.contacts.ContactsRepository

import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import android.provider.MediaStore
import com.hananelsaid.hp.thn2h.CreatGroups.CreatGroupsViewModel.CreatGroupViewModel
import com.hananelsaid.hp.thn2h.WhatsAppContacts
import com.hananelsaid.hp.thn2h.contacts.ContactModel.Contact
import com.hananelsaid.hp.thn2h.contacts.ContactsViewModel.ContactsViewModel
import com.hananelsaid.hp.thn2h.ui.ChooseReciver.ChooseContacts.ChooseContactsViewModel
import java.util.*
import kotlin.collections.ArrayList

class ContactsRepository {

    lateinit var contactViewModel: ContactsViewModel
    lateinit var creatGroupViewModel: CreatGroupViewModel
    lateinit var chooseContactsViewModel: ChooseContactsViewModel


    constructor(contactViewModel: ContactsViewModel) {
        this.contactViewModel = contactViewModel
    }

    constructor(creatGroupViewModel: CreatGroupViewModel) {
        this.creatGroupViewModel = creatGroupViewModel
    }

    constructor(chooseContactsViewModel: ChooseContactsViewModel) {
        this.chooseContactsViewModel = chooseContactsViewModel
    }


    fun getContactsList(context: Context): List<Contact> {

        val contactList: MutableList<Contact> = ArrayList()
        val contacts = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null, null
        )
        while (contacts!!.moveToNext()) {
            val name =
                contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val number =
                contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val obj = Contact()
            obj.name = name
            obj.number = number

            val photo_uri =
                contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))
            if (photo_uri != null) {
                obj.image = MediaStore.Images.Media.getBitmap(
                    context.contentResolver,
                    Uri.parse(photo_uri)
                )
            }
            contactList.add(obj)
        }

        //contact_list.adapter = ContactAdapter(contactList,this)
        contacts.close()

        Collections.sort(contactList, object : Comparator<Contact> {
            override fun compare(o1: Contact, o2: Contact): Int {
                return o1.name.compareTo(o2.name)
            }
        })
        object : Thread() {
            override fun run() {
                val whatsAppList = WhatsAppContacts.whatsAppList(context!!)

               for ( contact:Contact in contactList){
                   var number=contact.number
                contact.IsWhatsappContact = whatsAppList.contains(number)}
            }
        }.start()


        return contactList
    }

}




