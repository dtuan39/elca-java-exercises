import { Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import {Observable, throwError} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ErrorHandlingService {
  handleError(error: HttpErrorResponse): void {
    let errorMessage = 'An unknown error occurred';

    if (error.error instanceof ErrorEvent) {
      // Client-side error
      errorMessage = error.error.message;
    } else {
      // Server-side error
      errorMessage = error.error.message || errorMessage;
    }

    // Handle or display the error message as required (e.g., show a toast notification, update UI, etc.)
    console.error(errorMessage);
  }

  extractErrorMessage(error: HttpErrorResponse): string {
    let errorMessage = 'An unknown error occurred';

    if (error.error instanceof ErrorEvent) {
      // Client-side error
      errorMessage = error.error.message;
    } else {
      // Server-side error
      errorMessage = error.error.message || errorMessage;
    }

    return errorMessage;
  }
}
