package joshie.enchiridion.books.buttons;

import java.util.List;

import joshie.enchiridion.api.EnchiridionAPI;
import joshie.enchiridion.api.IPage;
import joshie.enchiridion.helpers.JumpHelper;

public class ButtonDeletePage extends AbstractButton {
    public ButtonDeletePage() {
        super("delete");
    }

    @Override
    public void performAction() {
        IPage currentPage = EnchiridionAPI.book.getPage();
        int numberOfPages = EnchiridionAPI.book.getBook().getPages().size();
        int pageNumber = 1;
        if (numberOfPages > 1) {
            pageNumber = getPreviousPage();
            JumpHelper.jumpToPageByNumber(pageNumber); //Jump to the previous page
            //Delete the older page
            EnchiridionAPI.book.getBook().removePage(currentPage);
        } else {
            EnchiridionAPI.book.getPage().clear();
        }
    }

    public int getPreviousPage() {
        List<IPage> pages = EnchiridionAPI.book.getBook().getPages();
        int number = EnchiridionAPI.book.getPage().getPageNumber() - 1;
        //Search the numbers
        while (number >= 0) {
            for (IPage page : pages) {
                if (page.getPageNumber() == number) {
                    return number;
                }
            }

            number--;
        }

        //If we failed to find the next available page, reset the book to page 1
        IPage maxPage = null;
        for (IPage page : pages) {
            if (maxPage == null || page.getPageNumber() > maxPage.getPageNumber()) {
                maxPage = page;
            }
        }

        return maxPage.getPageNumber();
    }

    @Override
    public boolean isLeftAligned() {
        return false;
    }
}
