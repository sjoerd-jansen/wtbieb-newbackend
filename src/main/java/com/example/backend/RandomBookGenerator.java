package com.example.backend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.backend.dto.BookCopy;

public class RandomBookGenerator
{
	private List<String> bookTitles = Arrays.asList(new String[]
			{
					"How to Find a Workspace for Dummies",
					"Know Your Meme: A Guide to Understanding Memes",
					"Java 101",
					"Kamasutra",
					"The Subtle Art of Not Giving a F*ck",
					"The Hitchhikers Guide to the Galaxy",
					"How to Rule Your Trainees",
					"Favorite Menus: The Cuisine of the Swamp",
					"Satan on Wheels",
					"How to Lie to Your Boss",
					"Case of the Invisible Corgi",
					"How to get Rich as a Software Developer",
					"How to get Away with Murder",
					"Understanding CSS: At Least You Tried",
					"Why Password Security Doesn't Matter",
					"The Ultimate Guide to Life",
					"Star Wars: Everything You Need to Know",
					"Coming Up With Book Titles: A Guide",
					"Why Creating Random Database Data is Boring",
					"Understanding the Spring Boot Framework",
					"Why We Should All Use Facebook: Not sponsored by Facebook (no really)",
					"Virtual Reality: Future or Flop",
					"How AR Helps Us Discover the World",
					"Why Android is Better than Apple",
					"A Guide to Hashing Passwords"
			});
	
	private List<String> authorNames = Arrays.asList(new String[] 
			{
				"Jorrit van der Kooi",
				"Wouter Schmeetz",
				"Arnold Savenije",
				"Lucas Schuit",
				"Tico van Hoek",
				"Sjoerd Jansen",
				"Jesse van den Ende",
				"Imane Al Gharib",
				"Ee Jie Tan"
			});
	
	private List<String> tags = Arrays.asList(new String [] 
			{
				"workspace",
				"dummies",
				"find",
				"meme",
				"guide",
				"understanding",
				"java",
				"101",
				"learning",
				"sex",
				"art",
				"galaxy",
				"hitchhikers",
				"rule",
				"trainees",
				"menus",
				"cuisine",
				"swamp",
				"favourite",
				"satan",
				"wheels",
				"lie",
				"boss",
				"how-to",
				"invisible",
				"corgi",
				"rich",
				"software",
				"developer",
				"murder",
				"css",
				"password",
				"security",
				"ultimate",
				"life",
				"star wars",
				"book",
				"titles",
				"database",
				"random",
				"boring",
				"spring",
				"framework",
				"facebook",
				"sponsored",
				"VR",
				"virtual reality",
				"AR",
				"augmented reality",
				"android",
				"apple",
				"ios",
				"hashing"				
			});
	
	private List<String> bookCovers = Arrays.asList(new String[]
			{
					"https://i.imgflip.com/261xbn.jpg",
					"https://images-na.ssl-images-amazon.com/images/I/612gSs9IIsL.jpg",
					"https://images-na.ssl-images-amazon.com/images/I/51CCTWpjTxL._SX342_SY445_QL70_ML2_.jpg",
					"https://d28hgpri8am2if.cloudfront.net/book_images/onix/cvr9781631584916/kama-sutra-9781631584916_hr.jpg",
					"https://images-na.ssl-images-amazon.com/images/I/51mN3bY0JjL._SX342_SY445_QL70_ML2_.jpg",
					"https://www.bruna.nl/images/active/carrousel/fullsize/9781529051438_front.jpg",
					"https://images-na.ssl-images-amazon.com/images/I/41KKJQ9YYrL._SX351_BO1,204,203,200_.jpg",
					"https://productimages.worldofbooks.com/1844009157.jpg",
					"https://pictures.abebooks.com/isbn/9780907526254-us.jpg",
					"https://images-na.ssl-images-amazon.com/images/I/91WXAKKPwSL.jpg",
					"https://images1.penguinrandomhouse.com/cover/9781101884553",
					"https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1356444202l/1837402.jpg",
					"https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1441297162l/26219914._SX318_.jpg",
					"https://miro.medium.com/max/1200/1*a7dOwhKpqP8pHLOJxR4OEw.png",
					"https://images-na.ssl-images-amazon.com/images/I/91+UNFD2-UL.jpg",
					"https://d28hgpri8am2if.cloudfront.net/book_images/onix/cvr9781646043125/florence-scovel-shinns-guide-to-life-9781646043125_hr.jpg",
					"https://images-na.ssl-images-amazon.com/images/I/6142QX6BhoL._SX413_BO1,204,203,200_.jpg",
					"https://edit.org/images/cat/book-covers-big-2019101610.jpg",
					"https://www.databasestar.com/wp-content/uploads/2019/12/database-design-mere-mortals.jpg",
					"https://images-na.ssl-images-amazon.com/images/I/41Mno4eQFkL._SX396_BO1,204,203,200_.jpg",
					"https://hips.hearstapps.com/ellnl.h-cdn.co/assets/15/37/2048x2730/2048x2730-d42ef870-36d9-11e6-b25f-a384eb0a7510media-images-1-app-6902923-1-eng-gb-1-app-jpg.jpg",
					"https://images-na.ssl-images-amazon.com/images/I/81CLg3vXNRL.jpg",
					"https://images.routledge.com/common/jackets/crclarge/978036750/9780367508104.jpg",
					"https://images-na.ssl-images-amazon.com/images/I/41sz2pOX10L._SY344_BO1,204,203,200_.jpg",
					"https://m.media-amazon.com/images/I/41BNWlT65HL.jpg"
			});
	
	public List<BookCopy> GenerateBooks()
	{
		List<BookCopy> books = new ArrayList<BookCopy>();
		
		for (int i = 0; i < bookTitles.size(); i++)
		{
			BookCopy newBook = new BookCopy();	
			
			int bookAuthor = (int)(Math.random()*authorNames.size());
			
			newBook.setBookTitle(bookTitles.get(i));
			newBook.setBookIsbn(GenerateISBN());
			newBook.setBookAuthor(authorNames.get(bookAuthor));
			newBook.setBookTags(GenerateTags());
			newBook.setBookCover(bookCovers.get(i));
			
			books.add(newBook);
		}
		
		return books;
	}
	
	private String GenerateISBN()
	{
		String ISBN = "";
		
		int firstDigits = (int) ((Math.random() * (999 - 100)) + 100);
		int secondDigit = (int) (Math.random() * 10);
		int thirdDigit = (int) ((Math.random() * (99 - 10)) + 10);
		int fourthDigit = (int) ((Math.random() * (999999 - 100000)) + 100000);
		int fifthDigit = (int) (Math.random() * 10);
		
		ISBN = firstDigits + "-" + secondDigit + "-" + thirdDigit + "-" + fourthDigit + "-" + fifthDigit;

		return ISBN;
	}
	
	private String[] GenerateTags()
	{
		String[] bookTags = new String[3];
		
		for (int i = 0; i < bookTags.length; i++)
			bookTags[i] = tags.get((int) (Math.random() * tags.size()));
		
		return bookTags;
	}
}
